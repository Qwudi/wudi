package com.hwq.wudi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//排除DataSource自动配置类,否则会默认自动配置,不会使用我们自定义的DataSource,并且启动报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ManagementApplication {

    public static void main(String[] args){
        SpringApplication.run(ManagementApplication.class, args);
    }

}
