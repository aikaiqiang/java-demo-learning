package com.kaywall.poi.ecxel.reader;

import com.kaywall.poi.ecxel.ExcelReaderUtil;
import com.kaywall.poi.ecxel.ExcelRowReader;
import com.kaywall.poi.ecxel.IExcelRowReader;
import com.kaywall.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelXlsxReader extends DefaultHandler {

    //  业务逻辑处理
    private IExcelRowReader rowReader;
    public void setRowReader(IExcelRowReader rowReader) {
        this.rowReader = rowReader;
    }

    /**
     * 共享字符串表
     */
    private SharedStringsTable sst;

    /**
     * 上一次的内容
     */
    private String lastContents;


    /**
     * 字符串标识
     */
    private boolean nextIsString;

    /**
     * 工作表索引
     */
    private int sheetIndex = -1;

    /**
     * 行集合
     */
    private List<String> rowlist = new ArrayList<String>();

    /**
     * 当前行号
     */
    private int curRow = 0;

    /**
     * 当前列
     */
    private int curCol = 0;

    /**
     * T元素标识
     */
    private boolean isTElement;

    /**
     * 异常信息，如果为空则表示没有异常
     */
    private String exceptionMessage;

    /**
     * 单元格数据类型，默认为字符串类型
     */
    private CellDataType nextDataType = CellDataType.SSTINDEX;


    private final DataFormatter formatter = new DataFormatter();

    private short formatIndex;

    private String formatString;

    // 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
    private String preRef = null, ref = null;


    // 定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
    private String maxRef = null;

    /**
     * 单元格
     */
    private StylesTable stylesTable;


    /**
     * 单元格中的数据可能的数据类型
     */
    enum CellDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }

    /**
     * 处理所有sheet
     * @param filename
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public void process(String filename) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader xssfReader = new XSSFReader(pkg);
        stylesTable = xssfReader.getStylesTable();
        SharedStringsTable sst = xssfReader.getSharedStringsTable();
        XMLReader parser = this.fetchSheetParser(sst);
        Iterator<InputStream> sheets = xssfReader.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }


    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
//        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        XMLReader parser = SAXHelper.newXMLReader();
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // c => 单元格
        if ("c".equals(qName)) {
            // 前一个单元格的位置
            if (preRef == null) {
                preRef = attributes.getValue("r");
            } else {
                preRef = ref;
            }
            // 当前单元格的位置
            ref = attributes.getValue("r");
            // 设定单元格类型
            this.setNextDataType(attributes);
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }

        // 当元素为t时
        if ("t".equals(qName)) {
            isTElement = true;
        } else {
            isTElement = false;
        }

        // 置空
        lastContents = "";
    }

    /**
     * 单元格数据类型
     * @param attributes
     */
    public void setNextDataType(Attributes attributes) {
        nextDataType = CellDataType.NUMBER;
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");
        String columData = attributes.getValue("r");

        if ("b".equals(cellType)) {
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) {
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }

        if (cellStyleStr != null) {
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            formatIndex = style.getDataFormat();
            formatString = style.getDataFormatString();

            if ("m/d/yy" == formatString) {
                nextDataType = CellDataType.DATE;
                formatString = "yyyy-MM-dd hh:mm:ss.SSS";
            }

            if (formatString == null) {
                nextDataType = CellDataType.NULL;
                formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
            }
        }
    }


    /**
     * 根据单元格数据类型解析单元格数据（时间，日期处理）
     * @param value 单元格的值（这时候是一串数字）
     * @param thisStr 一个空字符串
     * @return
     */
    public String getDataValue(String value, String thisStr) {
        switch (nextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL:
                char first = value.charAt(0);
                thisStr = first == '0' ? "FALSE" : "TRUE";
                break;
            case ERROR:
                thisStr = "\"ERROR:" + value + '"';
                break;
            case FORMULA:
                thisStr = '"' + value + '"';
                break;
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value);
                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX:
                String sstIndex = value;
                try {
                    int idx = Integer.parseInt(sstIndex);
                    XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
                    thisStr = rtss.toString();
                    rtss = null;
                } catch (NumberFormatException ex) {
                    thisStr = value;
                }
                break;
            case NUMBER:
                System.out.println("formatString:" + formatString);
                if (formatString != null) {
//                    if(checkFormatStringIfDate()){
//                        Double d = Double.parseDouble(value);
//                        if(d.compareTo(0d) == 0){
//                            thisStr = "00:00:00";
//                            break;
//                        }
//                        Date date = HSSFDateUtil.getJavaDate(d, false);
//                        // 判断时间序列号
//                        if(value.contains(".")){
//                            // 分两种情况 年月日 时分秒 和 时分秒
//                            if(d.compareTo(new Double(1)) > 0){
//                                thisStr = DateUtils.format(date, DateUtils.DATE_FORMAT_19);
//                            }else {
//                                thisStr = DateUtils.format(date, DateUtils.DATE_FORMAT_8_TIME);
//                            }
//                        }else {
//                            // 不包含小数点，只有年月日
//                            thisStr = DateUtils.format(date, DateUtils.DATE_FORMAT_10);
//                        }
//                    }else {
//                        thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
//                    }

                    thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();


                } else {
                    thisStr = value;
                }

                thisStr = thisStr.replace("_", "").trim();
                break;
            case DATE:
                thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);

                // 对日期字符串作特殊处理
                thisStr = thisStr.replace(" ", "T");
                break;
            default:
                thisStr = " ";

                break;
        }

        return thisStr;
    }


    /**
     * 检查 formatString 格式是否为时间/日期
     * @return
     */
    boolean checkFormatStringIfDate(){
        if(formatString.contains("h")){
            return true;
        }
        return false;
    }



    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (nextIsString && StringUtils.isNotEmpty(lastContents) && StringUtils.isNumeric(lastContents)) {
            int idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
        }

        // t元素也包含字符串
        if (isTElement) {
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            String value = lastContents.trim();
            rowlist.add(curCol, value);
            curCol++;
            isTElement = false;
        } else if ("v".equals(qName)) {
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            String value = this.getDataValue(lastContents.trim(), "");
            // 补全单元格之间的空单元格
            if (!ref.equals(preRef)) {
                int len = countNullCell(ref, preRef);
                for (int i = 0; i < len; i++) {
                    rowlist.add(curCol, "");
                    curCol++;
                }
            }
            rowlist.add(curCol, value);
            curCol++;
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (qName.equals("row")) {
                // 默认第一行为表头，以该行单元格数目为最大数目
                if (curRow == 0) {
                    maxRef = ref;
                }
                // 补全一行尾部可能缺失的单元格
                if (maxRef != null) {
                    int len = countNullCell(maxRef, ref);
                    for (int i = 0; i <= len; i++) {
                        rowlist.add(curCol, "");
                        curCol++;
                    }
                }
                rowReader.getRows(sheetIndex, curRow, rowlist);

                rowlist.clear();
                curRow++;
                curCol = 0;
                preRef = null;
                ref = null;
            }
        }

    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 判断2个空单元格之间的单元格数目(同一行)
     * @param ref
     * @param preRef
     * @return
     */
    public int countNullCell(String ref, String preRef) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        xfd_1 = fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res = (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]);
        return res - 1;
    }


    /**
     * 字符填充
     * @param str
     * @param len
     * @param let
     * @param isPre
     * @return
     */
    String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }

    /**
     * 获取异常信息
     * @return
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }


    public static void main(String[] args) {
        IExcelRowReader rowReader = new ExcelRowReader();
        try {
            System.out.println("===================07 excel====================");
            // 获取resource的文件流
//            ClassPathResource resource = new ClassPathResource("excelfile/BigDataExport50000.xlsx");
//            InputStream inputStream = resource.getInputStream();
            String sourceLocation = "/Users/aikaiqiang/Workspace/Java/Study/java-demo-learning/apache-poi-demo/src/main/resources/excel/";
            String fileLocation1 = sourceLocation + "数字.xlsx";
            String fileLocation2 = sourceLocation + "时间.xlsx";
            String fileLocation3 = sourceLocation + "测试特殊字符.xlsx";
            String fileLocation4 = sourceLocation + "特殊.xlsx";
            String fileLocation5 = sourceLocation + "自定义时间.xlsx";
            ExcelReaderUtil.readExcel(rowReader, fileLocation3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
