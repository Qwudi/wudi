package com.hwq.wudi.config.datasource;

/**
 * @Auther: haowenqiang
 * @Description:
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源持有类
 */
@Slf4j
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dbType){
        log.info("切换到[{}]数据源",dbType);
        contextHolder.set(dbType);
    }

    public static String getDataSource(){
        return contextHolder.get();
    }

    public static void clearDataSource(){
        contextHolder.remove();
    }
}
