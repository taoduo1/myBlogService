package com.example.shop_user.config.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-24 21:50
 */

@Configuration
public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    //队列 起名：UserDirectQueue
    @Bean
    public Queue UserDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue("UserDirectQueue", true);
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    DirectExchange UserDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange("UserDirectExchange", true, false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：UserDirectQueue
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(UserDirectQueue()).to(UserDirectExchange()).with("UserDirectRouting");
    }


    // 工作队列（不需要交换机）
    @Bean
    public Queue WorkQueue() {
        return new Queue("WorkQueue", true); // 持久化队列
    }

    // 如果需要使用交换机模式的工作队列（可选）
    @Bean
    DirectExchange WorkExchange() {
        return new DirectExchange("WorkExchange", true, false);
    }

    @Bean
    Binding bindingWorkQueue() {
        return BindingBuilder.bind(WorkQueue()).to(WorkExchange()).with("WorkDirectRouting");
    }

    // 发布订阅模式配置（Fanout Exchange）
    @Bean
    public Queue pubSubQueue1() {
        return new Queue("PubSubQueue1", true);
    }

    @Bean
    public Queue pubSubQueue2() {
        return new Queue("PubSubQueue2", true);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("FanoutExchange", true, false);
    }

    @Bean
    Binding bindingFanout1() {
        return BindingBuilder.bind(pubSubQueue1()).to(fanoutExchange());
    }

    @Bean
    Binding bindingFanout2() {
        return BindingBuilder.bind(pubSubQueue2()).to(fanoutExchange());
    }


    // 路由模式配置（Direct Exchange）
    @Bean
    public Queue routingQueue1() {
        return new Queue("RoutingQueue.A", true); // 持久化队列
    }

    @Bean
    public Queue routingQueue2() {
        return new Queue("RoutingQueue.B", true);
    }

    @Bean
    DirectExchange routingExchange() {
        return new DirectExchange("RoutingExchange", true, false); // 持久化交换机
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("TopicExchange", true, false); //主题模式
    }

    // 绑定队列到交换机，并指定路由键
    @Bean
    Binding bindingRoutingA() {
        return BindingBuilder.bind(routingQueue1())
                //.to(routingExchange()) //精确模式
                .to(topicExchange()) //主题模式
                .with("routing.key.*"); // 精确路由键
    }

    @Bean
    Binding bindingRoutingB() {
        return BindingBuilder.bind(routingQueue2())
                //.to(routingExchange()) //精确模式
                .to(topicExchange()) //主题模式
                .with("routing.key.*");
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        // 必须设置以下两个属性
        connectionFactory.setPublisherReturns(true);  // 开启返回模式
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发送成功：" + correlationData);
            } else {
                System.out.println("消息发送失败：" + cause);
            }
        });
        // 如果要设置Mandatory（确认消息路由到队列）回调，可以设置如下（注意：这需要设置publisherReturns为true）
         rabbitTemplate.setMandatory(true);
         rabbitTemplate.setReturnsCallback(returned -> {
             System.out.println("消息被退回：" + returned);
         });
        return rabbitTemplate;
    }
}