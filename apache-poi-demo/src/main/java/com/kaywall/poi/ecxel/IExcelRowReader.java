package com.kaywall.poi.ecxel;

import java.util.List;

/**
 *
 *@desc IExcelRowReader 接口
 *@author aikaiqiang
 *@date 2019-06-30 23:37
 *
 **/
public interface IExcelRowReader {

    /**
     * 获取excel每一行内容
     * @param sheetIndex
     * @param curRow
     * @param rowList
     */
    void getRows(int sheetIndex, int curRow, List<String> rowList);
}
