package com.hwq.wudi.util.excel.demo;

import com.hwq.wudi.util.excel.imports.annotation.ExcelImportCol;
import com.hwq.wudi.util.excel.imports.annotation.ExcelImportConfig;
import lombok.Data;

/**
 * @Auther: haowenqiang
 * @Description:
 */
@Data
@ExcelImportConfig
public class NewRetailExcel {

    @ExcelImportCol(colIndex = 1)
    private String mobile;

    @ExcelImportCol(nullable=false, colIndex = 2)
    private String name;
}
