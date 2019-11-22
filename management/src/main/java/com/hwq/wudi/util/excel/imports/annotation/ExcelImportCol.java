package com.hwq.wudi.util.excel.imports.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：Excel 导入属性注解类 
 * 1、导入的类必须添加注解类ExcelImportConfig
 * 2、该注解类用在类属性上，获取Excel所在列的记录
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
@Documented
public @interface ExcelImportCol {
	
	/**
	 * 描述：Excel 所在列索引
	 * @return Excel 所在列索引从0开始，即excel中A列索引是0
	 */
	int colIndex();

	/**
	 * excel中该列是否可为空，默认可空
     * 如果设为false，该列为空的话，那么正行数据就不导入。
	 * @return
	 */
	boolean nullable() default true;
}
