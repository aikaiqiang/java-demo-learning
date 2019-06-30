package com.kaywall.poi.ecxel;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ExcelRowReader implements IExcelRowReader{

    @Override
    public void getRows(int sheetIndex, int curRow, List<String> rowList) {
        System.out.print(">>>>>>>>>>当前行号 row = " + curRow + " :\n");
        for (int i = 0; i < rowList.size(); i++) {
            System.out.print(StringUtils.isEmpty(rowList.get(i)) ? "null " : rowList.get(i) + " ");
        }
        System.out.println();
    }
}
