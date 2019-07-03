package com.hwq.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class RabbitMqUtil {

    private static RabbitTemplate rabbitTemplate;
    private static AmqpAdmin amqpAdmin;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate,AmqpAdmin amqpAdmin) {
        RabbitMqUtil.rabbitTemplate = rabbitTemplate;
        RabbitMqUtil.amqpAdmin = amqpAdmin;
    }

    /**
     * 发送消息到交换机，指定routing key
     *
     * @param exchange
     * @param key
     * @param object
     * @return
     */
    public static Boolean sendJsonMsg(String exchangeName, String routingKey, Object msg) {
        try {
            String unique = UUID.randomUUID().toString();
            Message message = MessageBuilder.withBody(JSON.toJSONString(msg).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .setContentEncoding("utf-8")
                    .setMessageId(unique)
                    .build();
            rabbitTemplate.send(exchangeName, routingKey, message,new CorrelationData(unique));
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 生产消息到队列中
     *
     * @param queue
     * @param object
     * @return
     */
    public static Boolean sendJsonMsgQueue(String queue, Object object) {
        try {
            rabbitTemplate.convertAndSend(queue, object);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 创建队列
     *
     * @param queue
     * @return
     */
    public static String creatQueue(String queue) {
        return amqpAdmin.declareQueue(new Queue(queue, true));

    }


    /**
     * 创建 topic 类型的 交换机
     *
     * @param exchange
     * @return
     */
    public static Boolean creatTopicExchange(String exchange) {
        TopicExchange topicExchange = new TopicExchange(exchange, true, false);
        amqpAdmin.declareExchange(topicExchange);
        return true;

    }

    /**
     * 创建 Fanout 类型的 交换机
     *
     * @param exchange
     * @return
     */
    public static Boolean creatFanoutExchange(String exchange) {
        //创建交换机
        FanoutExchange topicExchange = new FanoutExchange(exchange, true, false);
        amqpAdmin.declareExchange(topicExchange);
        return true;
    }

    /**
     * 交换机与队列绑定，指定bindingkey
     * @param exchange
     * @param queue
     * @param key
     * @return
     */
    public static Boolean bind(String exchange, String queue, String bindingKey) {
        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE,
                exchange, bindingKey, new HashMap());
        amqpAdmin.declareBinding(binding);
        return true;
    }


    /**
     * 创建 Fanout 类型的 交换机 并且绑定 队列
     *
     * @param exchange
     * @return
     */
    public static Boolean creatFanoutExchange(String exchange, String key, String queue) {
        //创建交换机
        Boolean aBoolean = creatFanoutExchange(exchange);
        //创建队列
        String s = creatQueue(queue);
        //bind
        Boolean bind = bind(exchange, queue, key);
        return true;
    }

}
