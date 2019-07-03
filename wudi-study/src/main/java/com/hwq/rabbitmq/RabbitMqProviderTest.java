package com.hwq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/1
 * @Description:
 */
@RestController
@Slf4j
public class RabbitMqProviderTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;
    /**
     * direct 类型 ：根据bingding key 完全匹配
     */
    @GetMapping("/direct")
    public void creatDirectMq() {
        //声明exchange
        //默认durable = true持久化的 宕机数据存盘不丢失
        //默认autoDelete = fals 非自动删除
        //注意 自动删除的前提是至少有一个交换器或队列与这个交换器绑定，之后所有与这个交换器绑定的
        //队列或交换器都与此解绑，不能错误的理解为当连接断开时，自动删除这个交换器
        Exchange myDirect = new DirectExchange("wudi.direct.exchange");
        Exchange myTopic = new TopicExchange("wudi.topic.exchange", true, false);
        FanoutExchange myFanout = new FanoutExchange("wudi.fanout.exchange");
        //创建exchange，可重复执行
        amqpAdmin.declareExchange(myDirect);
        amqpAdmin.declareExchange(myTopic);
        amqpAdmin.declareExchange(myFanout);

        //声明queue
        //有多个构造方法，参数：
        //默认durable = true 同exchange
        //默认autoDelete = fals 至少有一个消费者连接到这个队里，当最后一个消费者断开连接时，删除队列
        //这里注意，不必须有消费者连接过，才删除
        //默认exclusive = false 如果设为true，那么该队列仅对首次声明他的连接可见，断开连接自动删除
        //这里需注意：1排他队列是基于连接可见的，同一个连接的多个channel可连接这个队列
        //2.其他连接不允许创建已经存在的同名的排他队列，这里与普通队列不同。
        //3.一但连接关闭或客户端退出，无论是否是持久化队列，该队列都会删除。
        //这种队列适用于同时发送和读取消息的场景。很少使用。
        Queue debugQueue = new Queue("wudi.debug");
        Queue infoQue = new Queue("wudi.info", true, false, false);
        Queue errorQueue = new Queue("wudi.error");
        amqpAdmin.declareQueue(debugQueue);
        amqpAdmin.declareQueue(infoQue);
        amqpAdmin.declareQueue(errorQueue);
        //创建binding
        //这里第一个参数为要绑定的queue或exchange 同第三个参数exchange绑定
        //exchange 也可以和exchange绑定。
        Binding debugBingding = new Binding("wudi.debug", Binding.DestinationType.QUEUE,
                "wudi.direct.exchange", "debugAndInfo", new HashMap<>());
        //BindingBuilder方式创建binding
        Binding infoBinding = BindingBuilder.bind(infoQue).to(myDirect).with("debugAndInfo").and(new HashMap<>());
        amqpAdmin.declareBinding(debugBingding);
        amqpAdmin.declareBinding(infoBinding);
        amqpAdmin.declareBinding(new Binding("wudi.error", Binding.DestinationType.QUEUE, "wudi.direct.exchange", "errorInfo", new HashMap<>()));
    }

    /**
     * topic 类型 根据binding key 完全匹配
     */
    @GetMapping("/topic")
    public void creatTopic() {
        Exchange myTopic = new TopicExchange("wudi.topic.exchange", true, false);
        //相同的exchange可以重复声明，存在就用已有的，不存在就创建
        amqpAdmin.declareExchange(myTopic);
        //声明俩队列
        amqpAdmin.declareQueue(new Queue("wudi.topic1.queue"));
        amqpAdmin.declareQueue(new Queue("wudi.topic2.queue"));
        //分别绑定到exchange上
        //topic1队列routeing key匹配：xxx.topic1.xxx.xxx.... 和topic1.xxx.xxx....
        amqpAdmin.declareBinding(new Binding("wudi.topic1.queue",
                Binding.DestinationType.QUEUE, "wudi.topic.exchange",
                "*.topic1.#", new HashMap<>()));
        //topic1队列routeing key匹配：xxx.xxx.topic2.xxx 和xxx.xxx.topic2
        amqpAdmin.declareBinding(new Binding("wudi.topic2.queue",
                Binding.DestinationType.QUEUE, "wudi.topic.exchange",
                "#.topic2.*", new HashMap<>()));
    }

    /**
     * fanout 类型的exchange 类似广播，忽略binding key 发送给所有绑定这个exchange的queue
     */
    @GetMapping("/fanout")
    public void creatFanout() {
        //相同的exchange可以重复声明，存在就用已有的，不存在就创建
        amqpAdmin.declareExchange(new FanoutExchange("wudi.fanout.exchange"));
        //声明俩队列
        amqpAdmin.declareQueue(new Queue("wudi.fanout1.queue"));
        amqpAdmin.declareQueue(new Queue("wudi.fanout2.queue"));
        //分别绑定到exchange上
        amqpAdmin.declareBinding(new Binding("wudi.fanout1.queue",
                Binding.DestinationType.QUEUE, "wudi.fanout.exchange",
                "fanout", new HashMap<>()));
        amqpAdmin.declareBinding(new Binding("wudi.fanout2.queue",
                Binding.DestinationType.QUEUE, "wudi.fanout.exchange",
                "fanout", new HashMap<>()));
    }

    /**
     * 发送消息
     * @param msg 内容
     * @param routingKey 路由键
     * @param exchangeName 发送到哪个交换器
     */
    @PostMapping("/send")
    public void send(String msg,String routingKey,String exchangeName){
        //一般情况消息都是传json字符串，自动转换为message对象。
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, JSON.toJSONString(msg));
        if(log.isDebugEnabled()){
            log.debug("向【{}】交换器发送routing key 为【{}】的消息：【{}】",exchangeName,routingKey,msg);
        }
        RabbitMqUtil.sendJsonMsg(exchangeName, routingKey, msg);
    }
}
