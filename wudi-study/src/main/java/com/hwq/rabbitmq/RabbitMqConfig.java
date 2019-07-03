package com.hwq.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/2
 * @Description:
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue pay() {
        return new Queue("pay", true);
    }
    @Bean
    public Queue order() {
        return new Queue("order", true);
    }
    @Bean
    public Exchange directExchange(){
        return new DirectExchange("wudi.direct.exchange",true,false);
    }
    @Bean
    public Binding binding(){
        return new Binding("order",Binding.DestinationType.QUEUE,
                "wudi.direct.exchange","order",new HashMap());
    }
    @Bean
    public Binding binding2(){
        return new Binding("pay",Binding.DestinationType.QUEUE,
                "wudi.direct.exchange","pay",new HashMap());
    }
}