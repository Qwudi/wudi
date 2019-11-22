package com.hwq.wudi.util.excel.imports;


import com.hwq.wudi.util.excel.imports.annotation.ExcelImportCol;
import com.hwq.wudi.util.excel.imports.annotation.ExcelImportConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 描述： Excel 导入工具类<br>
 * <br>
 * 方法一：excelImport(InputStream, Class) : 将 文件流 转化为 List对象集合,sheet索引默认为0；<br>
 * 方法一：excelImport(InputStream, Class, int) : 将 文件流 转化为 List对象集合,可设置sheet索引位置<br>
 */
@Slf4j
public class ExcelToolsImport {

    public static <T> List<T> excelImport(InputStream fileInputStream, Class<T> cla) {
        return excelImport(fileInputStream, cla, 0);
    }

    public static <T> List<T> excelImport(InputStream fileInputStream, Class<T> cla, int sheetIndex) {
        List<Field> fields = getExcelImportColAnnoFields(cla);

        checkValidate(fileInputStream, cla, fields);
        try (Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int rows = sheet.getLastRowNum();
            int startLine = getStartLine(cla);
            List<T> list = new ArrayList<>();
            Row row;
            T t;
            for (int i = startLine; i <= rows; i++) {
                row = sheet.getRow(i);
                t = addLine2List(row, cla, fields);

                if (validateNotNullCol(t, fields)) {
                    list.add(t);
                }
            }
            return list;
        } catch (EncryptedDocumentException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("该文件受保护，不能正确读取");
        } catch (InvalidFormatException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("该文件格式无法识别");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("文件数据读取错误");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("文件数据空值校验错误");
        }

    }

    /**
     * 获取sheet页的名称
     *
     * @param fileInputStream
     * @param cla
     * @param sheetIndex
     * @param <T>
     * @return
     */
    public static <T> String getSheetName(InputStream fileInputStream, Class<T> cla, int sheetIndex) {
        checkValidate(fileInputStream, cla, getExcelImportColAnnoFields(cla));
        try (Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            return sheet.getSheetName();
        } catch (EncryptedDocumentException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("该文件受保护，不能正确读取");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("文件数据读取错误");
        }
    }

    /**
     * 获取sheet页总数
     *
     * @param fileInputStream
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> Integer getNumberOfSheets(InputStream fileInputStream, Class<T> cla) {
        checkValidate(fileInputStream, cla, getExcelImportColAnnoFields(cla));
        try (Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            return workbook.getNumberOfSheets();
        } catch (EncryptedDocumentException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("该文件受保护，不能正确读取");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("文件数据读取错误");
        }
    }

    private static <T> T addLine2List(Row row, Class<T> cla, List<Field> fields) throws Exception {
        T t = cla.newInstance();
        for (Field field : fields) {
            setCell2Obj(field, row, t);
        }
        return t;
    }

    private static <T> void setCell2Obj(Field field, Row row, T t) throws Exception {
        int col = field.getAnnotation(ExcelImportCol.class).colIndex();
        Cell cell = row.getCell(col);
        if (cell != null) {
            String propertyName = field.getName();
            Class<?> fieldType = field.getType();
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, t.getClass());
            Method m = pd.getWriteMethod();
            switch (cell.getCellType()) {
                case STRING:
                    String value = cell.getRichStringCellValue().getString();
                    m.invoke(t, value);
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        m.invoke(t, date);
                    } else {
                        double d = cell.getNumericCellValue();
                        if (BigDecimal.class.isAssignableFrom(fieldType)) {
                            BigDecimal bigDecimal = BigDecimal.valueOf(d);
                            m.invoke(t, bigDecimal);
                        }
                        if ((Double.class.isAssignableFrom(fieldType)) || (double.class.isAssignableFrom(fieldType))) {
                            m.invoke(t, d);
                        }
                        if ((Float.class.isAssignableFrom(fieldType)) || (float.class.isAssignableFrom(fieldType))) {
                            m.invoke(t, (float) d);
                        }
                        if ((Integer.class.isAssignableFrom(fieldType)) || (int.class.isAssignableFrom(fieldType))) {
                            m.invoke(t, BigDecimal.valueOf(d).intValue());
                        }
                        if ((Long.class.isAssignableFrom(fieldType)) || (long.class.isAssignableFrom(fieldType))) {
                            m.invoke(t, BigDecimal.valueOf(d).longValue());
                        }
                        if (String.class.isAssignableFrom(fieldType)) {
                            m.invoke(t, BigDecimal.valueOf(d).longValue() + "");
                        }
                    }
                    break;
                case BOOLEAN:
                    Boolean b = cell.getBooleanCellValue();
                    m.invoke(t, b);
                    break;
                /**
                 * 处理单元格公式计算类型
                 */
                case FORMULA:
                    double formulaValue = cell.getNumericCellValue();
                    if (BigDecimal.class.isAssignableFrom(fieldType)) {
                        BigDecimal bigDecimal = BigDecimal.valueOf(formulaValue);
                        m.invoke(t, bigDecimal);
                    }
            }
        }
    }

    private static <T> int getStartLine(Class<T> cla) {
        return cla.getAnnotation(ExcelImportConfig.class).startLine();
    }

    /**
     * 获取实体类中所有ExcelImportCol注解标注的字段（需要导入的字段）
     *
     * @param cla
     * @param <T>
     * @return
     */
    private static <T> List<Field> getExcelImportColAnnoFields(Class<T> cla) {
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = cla.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ExcelImportCol.class)) {
                fieldList.add(f);
            }
        }
        return fieldList;
    }

    private static <T> void checkValidate(InputStream fileInputStream, Class<T> cla, List<Field> fields) {
        if (fileInputStream == null) {
            throw new RuntimeException("导入Excel生成的文件流为空！");
        }
        if (!cla.isAnnotationPresent(ExcelImportConfig.class)) {
            throw new RuntimeException("指定的实体类" + cla.getName() + " 缺少ExcelImportConfig注解！");
        }
        if (fields.size() == 0) {
            throw new RuntimeException("指定的实体类" + cla.getName() + " 属性缺少ExcelImportCol注解！");
        }
    }


    private static <T> boolean validateNotNullCol(T t, List<Field> fields) throws Exception {
        boolean validate = true;
        PropertyDescriptor pd;
        Method m;
        Object fieldValue;
        boolean nullable;
        for (Field f : fields) {
            nullable = f.getAnnotation(ExcelImportCol.class).nullable();
            if (!nullable) {
                pd = new PropertyDescriptor(f.getName(), t.getClass());
                m = pd.getReadMethod();
                fieldValue = m.invoke(t);
                if (fieldValue == null) {
                    validate = false;
                    break;
                }
            }
        }
        return validate;
    }
}
