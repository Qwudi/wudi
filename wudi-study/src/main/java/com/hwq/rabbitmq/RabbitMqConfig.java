package com.hwq.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Auther: haowenqiang
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
    public Exchange directExchange() {
        return new DirectExchange("wudi.direct.exchange", true, false);
    }

    @Bean
    public Binding binding() {
        return new Binding("order", Binding.DestinationType.QUEUE,
                "wudi.direct.exchange", "order", new HashMap());
    }

    @Bean
    public Binding binding2() {
        return new Binding("pay", Binding.DestinationType.QUEUE,
                "wudi.direct.exchange", "pay", new HashMap());
    }
    //如果设置了retry:enabled: true  重试最大次数后，仍然报错，会调用此方法。转发，
    //但消息除了会转发，也回原队列，等待重新推送，这样容易造成重复消费的问题。
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        RepublishMessageRecoverer recoverer = new RepublishMessageRecoverer(rabbitTemplate, "wudi.direct.exchange", "error");
        return recoverer;
    }
}