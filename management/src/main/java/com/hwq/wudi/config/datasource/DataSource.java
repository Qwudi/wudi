package com.hwq.wudi.config.datasource;

import java.lang.annotation.*;

/**
 * 切换数据源的注解
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "";
}
