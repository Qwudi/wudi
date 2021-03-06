package com.hwq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class StudyApplication {

    public static void main(String[] args){
        SpringApplication.run(StudyApplication.class, args);
    }

}
