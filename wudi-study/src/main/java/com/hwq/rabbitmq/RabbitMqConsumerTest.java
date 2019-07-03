package com.hwq.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/2
 * @Description:
 */
@Component
public class RabbitMqConsumerTest {
    @RabbitListener(queues ={"order"})
    public void handleMessage(Message message){
        System.out.println("====消费消息"+message.getMessageProperties().getConsumerQueue()+"===handleMessage");
        System.out.println(message.getMessageProperties());
        System.out.println(new String(message.getBody()));
    }
    @RabbitListener(queues ={"pay"})
    public void handleMessage2(Message message, Channel channel) throws IOException {
        System.out.println(new String(message.getBody()));
        System.out.println(message.getMessageProperties().getDeliveryTag());
        System.out.println(message.toString());
        //告诉服务器收到这条消息 已经被我成功消费了 可以在队列删掉 这样以后就不会再发了.
        //第二个参数为true标识确认所有concumer获得的消息，false为只确认当前一个消息
        //消费成功
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        //消费失败，一般在发生异常的chach中使用，此时告诉mq，消费出现异常，mq会无限次重发
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //拒绝消息
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);

    }
}
