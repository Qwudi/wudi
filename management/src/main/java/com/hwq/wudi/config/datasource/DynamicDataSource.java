package com.hwq.wudi.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: haowenqiang
 * @Date: 2019/9/2
 * @Description:
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceContextHolder.getDataSource();
        if (dataSource == null) {
            log.info("当前数据源为[primary]");
        } else {
            log.info("当前数据源为{}", dataSource);
        }
        return dataSource;
    }

}
