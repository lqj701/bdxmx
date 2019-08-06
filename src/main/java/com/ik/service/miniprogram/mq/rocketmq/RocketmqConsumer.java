package com.ik.service.miniprogram.mq.rocketmq;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQMessageListener(topic = "bdxmx", consumerGroup = "group2", consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING)
public class RocketmqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }

}
