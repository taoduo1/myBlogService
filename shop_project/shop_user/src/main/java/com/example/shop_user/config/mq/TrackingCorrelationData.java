package com.example.shop_user.config.mq;

import org.springframework.amqp.rabbit.connection.CorrelationData;

public class TrackingCorrelationData extends CorrelationData {

    private final String messageType;
    private final Object businessEntity;
    private final long sendTime;
    private int retryCount;

    public TrackingCorrelationData(String id, String messageType, Object entity) {
        super(id);
        this.messageType = messageType;
        this.businessEntity = entity;
        this.sendTime = System.currentTimeMillis();
    }

    // 监控标记方法
    public String getTrackingId() {
        return messageType + "-" + getId();
    }
}