package com.hwq.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/2
 * @Description:
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public List<Queue> queues(){
        List<Queue> queueList = new ArrayList<>();
        queueList.add(new Queue("wudi.debug",true));
        queueList.add(new Queue("wudi.info",true));
        queueList.add(new Queue("wudi.error",true));
        queueList.add(new Queue("wudi.topic1.queue",true));
        queueList.add(new Queue("wudi.topic2.queue",true));
        return queueList;
    }

    @Bean
    public List<Exchange> exchanges(){
        List<Exchange> exchangeList = new ArrayList<>();
        exchangeList.add(new DirectExchange("wudi.direct.exchange",true,false));
        exchangeList.add(new TopicExchange("wudi.topic.exchange",true,false));
        return exchangeList;
    }

    @Bean
    public List<Binding> bindings(){
        List<Binding> bindingList = new ArrayList<>();
        bindingList.add(BindingBuilder.bind(new Queue("wudi.debug")).
                to(new DirectExchange("wudi.direct.exchange")).with("debugAndInfo"));
        bindingList.add(BindingBuilder.bind(new Queue("wudi.info")).
                to(new TopicExchange("wudi.direct.exchange")).with("debugAndInfo"));
        bindingList.add(BindingBuilder.bind(new Queue("wudi.error")).
                to(new TopicExchange("wudi.direct.exchange")).with("error"));
        bindingList.add(BindingBuilder.bind(new Queue("wudi.topic1.queue")).
                to(new TopicExchange("wudi.topic.exchange")).with("*.topic1.#"));
        bindingList.add(BindingBuilder.bind(new Queue("wudi.topic2.queue")).
                to(new TopicExchange("wudi.topic.exchange")).with("#.topic2.*"));
        return bindingList;
    }
}
