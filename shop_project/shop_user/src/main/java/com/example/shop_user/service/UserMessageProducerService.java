package com.example.shop_user.service;

public interface UserMessageProducerService {

    /**
     * 点对点模式
     * @param userMessage 消息内容
     */
    void sendUserMessage(String userMessage);

    /**
     * 工作队列模式
     * @param task 消息内容
     */
    void sendWorkerMessage(String task);

    /**
     * 发布订阅模式
     * @param message 消息内容
     */
    void publishMessage(String message);


    /**
     * 路由模式配置
     * @param routingKey 路由键
     * @param message 消息
     */
    void sendByRouting(String routingKey, String message);
}
