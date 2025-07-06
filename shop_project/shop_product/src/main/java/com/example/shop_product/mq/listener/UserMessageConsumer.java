package com.example.shop_product.mq.listener;

import com.example.shop_common.utils.JSONUtils;
import com.example.shop_product.thirdto.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-24 21:53
 */
@Component
public class UserMessageConsumer {

    /**
     * 点对点模式 手动确认模式（需在application.yml配置acknowledge-mode: manual）
     */
    @RabbitListener(queues = "UserDirectQueue")
    public void receiveWithAck(Message message, Channel channel) throws IOException {
        try {
            System.out.println("收到消息: " + new String(message.getBody()));
            User user = JSONUtils.fromJSONString(new String(message.getBody()),User.class);
            // 业务处理...
            // processMessage(message);

            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 处理失败，拒绝消息（可设置是否重新入队）
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true); // true表示重新入队
        }
    }


    /**
     * 消费者1（会与消费者2竞争消费消息）
     */
    @RabbitListener(queues = "WorkQueue")
    public void worker1(Message message, Channel channel) throws IOException {
        try {
            System.out.println("Worker1 收到消息: " + new String(message.getBody()));
            User user = JSONUtils.fromJSONString(new String(message.getBody()),User.class);
            // 业务处理...
            // processMessage(message);

            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 处理失败，拒绝消息（可设置是否重新入队）
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true); // true表示重新入队
        }
    }

    @RabbitListener(queues = "WorkQueue")
    public void worker2(Message message, Channel channel) throws IOException {
        try {
            System.out.println("Worker2 收到消息: " + new String(message.getBody()));
            User user = JSONUtils.fromJSONString(new String(message.getBody()),User.class);
            // 业务处理...
            // processMessage(message);

            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 处理失败，拒绝消息（可设置是否重新入队）
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true); // true表示重新入队
        }
    }


    /**
     * 订阅者1
     */
    @RabbitListener(queues = "PubSubQueue1")
    public void subscriber1(Message message, Channel channel) throws IOException {
        try {
            System.out.println("订阅者1收到: " + new String(message.getBody()));
            User user = JSONUtils.fromJSONString(new String(message.getBody()),User.class);
            // 业务处理...
            // processMessage(message);

            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 处理失败，拒绝消息（可设置是否重新入队）
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true); // true表示重新入队
        }
    }

    /**
     * 订阅者2
     */
    @RabbitListener(queues = "PubSubQueue2")
    public void subscriber2(Message message, Channel channel) throws IOException {
        try {
            System.out.println("订阅者2收到: " + new String(message.getBody()));
            User user = JSONUtils.fromJSONString(new String(message.getBody()),User.class);
            // 业务处理...
            // processMessage(message);

            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 处理失败，拒绝消息（可设置是否重新入队）
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true); // true表示重新入队
        }
    }

    //消费者ACK：
    @RabbitListener(queues = "RoutingQueue.A")
    public void consumerA(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("消费者A收到: " + message);
        try {
            // 业务处理
            channel.basicAck(tag, false); // 手动确认
        } catch (Exception e) {
            channel.basicNack(tag, false, true); // 重试
        }
    }


    @RabbitListener(queues = "RoutingQueue.B")
    public void consumerB(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("消费者B收到: " + message);
        try {
            // 业务处理
            channel.basicAck(tag, false); // 手动确认
        } catch (Exception e) {
            channel.basicNack(tag, false, true); // 重试
        }
    }

}