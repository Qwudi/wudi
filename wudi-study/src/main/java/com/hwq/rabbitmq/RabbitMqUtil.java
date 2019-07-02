package com.hwq.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqUtil {
    private static RabbitTemplate rabbitTemplate;
    private static RabbitAdmin rabbitAdmin;
    @Autowired
    ConnectionFactory connectionFactory;
    @Autowired
    public RabbitMqUtil(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息到交换机，并且定义绑定key
     *
     * @param exchange
     * @param key
     * @param object
     * @return
     */
    public static Boolean sendJsonMsg(String exchange, String key, Object object) {
        try {
            rabbitTemplate.convertAndSend(exchange, key, JSON.toJSONString(object));
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return true;
    }
}
