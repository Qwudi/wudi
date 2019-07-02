package com.hwq.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/2
 * @Description:
 */
@Component
public class RabbitMqConsumerTest {
    @RabbitListener(queues ={"wudi.debug","wudi.info"})
    public void handleMessage(Message message){
        System.out.println("====消费消息"+message.getMessageProperties().getConsumerQueue()+"===handleMessage");
        System.out.println(message.getMessageProperties());
        System.out.println(new String(message.getBody()));
    }
}
