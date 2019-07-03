package com.hwq.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/3
 * @Description:
 */
@Component
//@Configuration
public class ComponentConfigeration {
    @Bean
    public BenzCar benzcar(){
        return new BenzCar();
    }
    @Bean
    public Car car(){
        return new Car(benzcar());
    }

}
