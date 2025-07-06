package com.example.shop_user.service.impl;

import com.example.shop_user.service.UserMessageProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageProducerServiceImpl implements UserMessageProducerService {

    private static final Logger logger = LoggerFactory.getLogger(UserMessageProducerServiceImpl.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void sendUserMessage(String userMessage) {
        // 使用配置中定义的交换机和路由键
        rabbitTemplate.convertAndSend(
                "UserDirectExchange",    // 交换机名称
                "UserDirectRouting",     // 路由键
                userMessage,             // 消息内容
                message -> {
                    // 可选：设置消息属性
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                });

        System.out.println("发送消息: " + userMessage);
    }

    @Override
    public void sendWorkerMessage(String task) {
        rabbitTemplate.convertAndSend("WorkExchange", "WorkDirectRouting", task);
    }

    @Override
    public void publishMessage(String message) {
        // Fanout交换机不需要routingKey
        rabbitTemplate.convertAndSend(
                "FanoutExchange",
                "", // 空路由键
                message,
                msg -> {
                    msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return msg;
                });
    }

    @Override
    public void sendByRouting(String routingKey, String message) {
        logger.info("发送消息：key = 【{}】 value = 【{}】",routingKey,message);
        rabbitTemplate.convertAndSend(
                "TopicExchange",
                routingKey, // 关键：指定路由键
                message,
                msg -> {
                    msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return msg;
                });
    }

    @Override
    public void sendWithConfirm(String routingKey, Object message) {
        // 发送消息，并设置correlationData，可以使用业务ID等
        rabbitTemplate.convertAndSend("TopicExchange", routingKey, message, messagePostProcessor -> {
            // 可以在此处设置消息属性，如设置消息ID等
            return messagePostProcessor;
        }, new CorrelationData("correlation-id-1")); // 此处可以传入一个关联数据，比如业务ID

        // 也可以不传递CorrelationData
        // rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }


}
