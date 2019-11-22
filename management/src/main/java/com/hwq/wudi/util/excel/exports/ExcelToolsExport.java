package com.hwq.wudi.util.excel.exports;

import com.hwq.wudi.util.excel.exports.annotation.ExcelExportCol;
import com.hwq.wudi.util.excel.exports.annotation.ExcelExportConfig;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 描述：Excel 导出工具类
 *
 */
public class ExcelToolsExport {
    private static final String DATE_FORMAT = "yyyy-mm-dd HH:mm:ss";
    private static final String NUMERIC_FORMAT = "#############0.00######";
    private static final int BUFFER_SIZE = 40960;

    private ExcelToolsExport() {

    }

    public static <T> byte[] createExcelExport(List<T> list, String sheetName) throws Exception {
        checkValidate(list);

        Workbook wb = createWorkbook(true);
        sheetName = (sheetName == null) || (sheetName.equals("")) ? "sheet1" : sheetName;
        Sheet sheet = wb.createSheet(sheetName);
        setExcelHeader(sheet, list.get(0));
        setExcelLines(sheet, list, wb);

        return getByteFormWb(wb);
    }

    public static byte[] createExcelExport(Map<String, List<?>> map) throws Exception {
        if ((map != null) && (map.size() > 0)) {
            Workbook wb = createWorkbook(true);

            Iterator<String> it = map.keySet().iterator();
            Sheet sheet;
            String sheetName;
            List<?> list;
            while (it.hasNext()) {
                sheetName = it.next();
                list = map.get(sheetName);

                checkValidate(list);

                sheet = wb.createSheet(sheetName);
                setExcelHeader(sheet, list.get(0));
                setExcelLines(sheet, list, wb);
            }
            return getByteFormWb(wb);
        }
        return null;
    }

    private static <T> void setExcelLines(Sheet sheet, List<T> list, Workbook wb) throws Exception {
        int lineStartRow = getLineStartRow(list.get(0).getClass());
        Row row;
        for (T aList : list) {
            row = sheet.createRow(lineStartRow);
            obj2Cell(row, aList, wb);
            lineStartRow++;
        }
    }

    private static <T> void setExcelHeader(Sheet sheet, T t) {
        int headRow = getHeaderRow(t.getClass());
        Row row = sheet.createRow(headRow);

        List<Field> list = getExcelExportColAnnoFields(t.getClass());
        ExcelExportCol excelExportCol;
        int cols;
        String colHeaderDesc;
        for (Field f : list) {
            excelExportCol = f.getAnnotation(ExcelExportCol.class);
            cols = excelExportCol.colIndex();
            colHeaderDesc = excelExportCol.colHeaderDesc();
            row.createCell(cols).setCellValue(colHeaderDesc);
        }
    }

    private static <T> void obj2Cell(Row row, T t, Workbook wb) throws Exception {
        List<Field> list = getExcelExportColAnnoFields(t.getClass());

        ExcelExportCol excelExportCol;
        Cell cell;
        int cols;
        Object value;
        PropertyDescriptor pd;
        Method m;
        for (Field f : list) {
            excelExportCol = f.getAnnotation(ExcelExportCol.class);
            cols = excelExportCol.colIndex();

            pd = new PropertyDescriptor(f.getName(), t.getClass());
            m = pd.getReadMethod();
            value = m.invoke(t);
            if (value == null) {
                continue;
            }
            cell = row.createCell(cols);
            fillCell(value, cell, wb);
        }
    }

    private static void fillCell(Object value, Cell cell, Workbook wb) {
        if ((value instanceof java.util.Date)) {
            java.util.Date d = (java.util.Date) value;
            DataFormat format = wb.createDataFormat();

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(format.getFormat(NUMERIC_FORMAT));

            cell.setCellStyle(cellStyle);
            cell.setCellValue(d);
            return;
        }
        if ((value instanceof BigDecimal)) {
            BigDecimal b = (BigDecimal) value;
            cell.setCellValue(b.doubleValue());
            return;
        }
        if ((value instanceof Double)) {
            Double d = (Double) value;
            DataFormat format = wb.createDataFormat();

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(format.getFormat("#############0.00######"));

            cell.setCellStyle(cellStyle);
            cell.setCellValue(d);
            return;
        }
        if ((value instanceof Float)) {
            Float f = (Float) value;
            DataFormat format = wb.createDataFormat();

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(format.getFormat("#############0.00######"));

            cell.setCellStyle(cellStyle);
            cell.setCellValue(f);
            return;
        }
        if ((value instanceof Long)) {
            Long l = (Long) value;
            cell.setCellValue(l);
            return;
        }
        if ((value instanceof Integer)) {
            Integer i = (Integer) value;
            cell.setCellValue(i);
            return;
        }
        if ((value instanceof Boolean)) {
            Boolean b = (Boolean) value;
            cell.setCellValue(b);
            return;
        }
        if ((value instanceof String)) {
            String s = (String) value;
            cell.setCellValue(s);
        }
    }

    private static Workbook createWorkbook(boolean flag) {
        Workbook wb;
        if (flag) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        return wb;
    }

    private static byte[] getByteFormWb(Workbook wb) throws Exception {
        if (wb != null) {
            ByteArrayOutputStream byStream = new ByteArrayOutputStream(40960);
            wb.write(byStream);
            return byStream.toByteArray();
        }
        return null;
    }

    private static <T> int getHeaderRow(Class<T> cla) {
        return cla.getAnnotation(ExcelExportConfig.class).headerRow();
    }

    private static <T> int getLineStartRow(Class<T> cla) {
        return cla.getAnnotation(ExcelExportConfig.class).lineStartRow();
    }

    private static <T> List<Field> getExcelExportColAnnoFields(Class<T> cla) {
        List<Field> list = new ArrayList<>();
        Field[] fields = cla.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ExcelExportCol.class)) {
                list.add(f);
            }
        }
        return list;
    }

    private static void checkValidate(List<?> list) throws Exception {
        if ((list == null) || (list.size() == 0)) {
            throw new Exception("指定的对象集合数据源为null，或者长度等于0！");
        }
        Class<?> cla = list.get(0).getClass();
        if (!cla.isAnnotationPresent(ExcelExportConfig.class)) {
            throw new Exception("指定的实体类" + list.get(0).getClass().getName() + " 缺少ExcelExportConfig注解！");
        }
        int headerRow = getHeaderRow(cla);
        int lineStartRow = getLineStartRow(cla);
        if (headerRow >= lineStartRow) {
            throw new Exception("指定的实体类" + cla.getName() + " 设置的标题头行应该小于内容记录开始行！");
        }
        if (getExcelExportColAnnoFields(cla).size() == 0) {
            throw new Exception("指定的实体类" + cla.getName() + " 属性缺少ExcelExportCol注解！");
        }
    }
}
