package com.hwq.wudi.util.excel.demo;

import com.hwq.wudi.util.excel.imports.ExcelToolsImport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class ImportDemo {

    public static void main(String[] args) throws FileNotFoundException {
        String fullPath = "C:\\Users\\35031\\Desktop\\newretail\\社交新零售手机号表.xlsx";
        FileInputStream fileInputStream = new FileInputStream(new File(fullPath));
        List<NewRetailExcel> newRetailExcels = ExcelToolsImport.excelImport(fileInputStream, NewRetailExcel.class);
        newRetailExcels.forEach(System.out::println);
        System.out.println(newRetailExcels.size());
    }
}
