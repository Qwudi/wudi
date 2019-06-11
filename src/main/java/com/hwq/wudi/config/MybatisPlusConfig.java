package com.hwq.wudi.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: haowenqiang
 * @Date: 2019/6/11 0011 23:52
 * @Description:
 */
// 声明配置类
@Configuration
@MapperScan("com.hwq.wudi.mapper")
public class MybatisPlusConfig {
    //覆盖分页组件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        //声明方言类型
        page.setDialectType("mysql");
        return page;
    }

    /**
     * 性能分析拦截器，不建议生产使用
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }
}
