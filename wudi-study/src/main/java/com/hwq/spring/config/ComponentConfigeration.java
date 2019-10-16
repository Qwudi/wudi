package com.hwq.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: haowenqiang
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
