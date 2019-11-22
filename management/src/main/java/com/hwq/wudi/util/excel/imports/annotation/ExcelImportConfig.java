package com.hwq.wudi.util.excel.imports.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：Excel 导入注解类: 
 * 
 * 1、导入的类必须添加该注解类 
 * 2、默认从第1行导入 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
public @interface ExcelImportConfig {
	
	/**
	 * 描述：读取Excel数据记录的开始行，默认为1，表头是索引是0
	 * @return 有效数据记录的开始行
	 */
	
	int startLine() default 1;

}
