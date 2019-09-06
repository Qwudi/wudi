package com.hwq.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/2
 * @Description:
 */
@Component
@Slf4j
public class RabbitMqConsumerTest {
    @RabbitListener(queues ={"order"})
    public void handleMessage(Message message, Channel channel) throws IOException {
        System.out.println("====1消费消息"+message.getMessageProperties().getConsumerQueue()+"===handleMessage");
        System.out.println(message.getMessageProperties());
        System.out.println(new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
//    @RabbitListener(queues ={"pay"})
//    public void handleMessage2(Message message, Channel channel) throws IOException, InterruptedException {
//        channel.basicQos(4);
//        System.out.println("handleMessage2 接收消息==" + new String(message.getBody()));
//        System.out.println("handleMessage2 接收消息==" + message.getMessageProperties().getDeliveryTag());
//        if(message.getMessageProperties().getDeliveryTag()%3 == 2){
//            System.out.println(message.getMessageProperties().getDeliveryTag()+"休息5秒");
//            TimeUnit.SECONDS.sleep(5);
//        }
//        System.out.println(message.getMessageProperties().getDeliveryTag()+"执行完成");
//        //告诉服务器收到这条消息 已经被我成功消费了 可以在队列删掉 这样以后就不会再发了.
//        //第二个参数为true标识确认所有concumer获得的消息，false为只确认当前一个消息
//        //消费成功
//      channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        //消费失败，一般在发生异常的chach中使用，此时告诉mq，消费出现异常，mq会无限次重发
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//        //拒绝消息
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//          throw new RuntimeException("fasheng yichang");
////        try {
////            if(message.getMessageProperties().getDeliveryTag() == 4){
////                throw new RuntimeException("fasheng yichang");
////            }
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
////        }catch (Exception e){
////            log.error(e.getMessage());
////            //转发到error queue-->存数据库-->定时重试还是手动处理都可以
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
////        }
//    }
    @RabbitListener(queues ={"pay"})
    public void handleMessage2(Message message, Channel channel) throws IOException, InterruptedException {
        System.out.println("handleMessage2 接收消息==" +message.getMessageProperties().getDeliveryTag());
        TimeUnit.SECONDS.sleep(5);
        System.out.println("handleMessage2消费执行完成"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        //0表示对消息的大小无限制，1表示每次只允许消费一条，
        // 第三个参数false标识对当前concumer有效，ture标识对channel有效。
        //从资料里查到，在这里设置QOS 似乎不起作用！
//        channel.basicQos(0, 1, false);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
    @RabbitListener(queues ={"pay"})
    public void handleMessage3(Message message, Channel channel) throws IOException, InterruptedException {
        System.out.println("handleMessage3 接收消息==" + message.getMessageProperties().getDeliveryTag());
        System.out.println("handleMessage3 消费执行完成"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        channel.basicQos(0, 1, false);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
