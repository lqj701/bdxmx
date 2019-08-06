package com.ik.service.miniprogram.mq.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RocketmqProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendUserMsg(String message) {
        rocketMQTemplate.syncSend("bdxmx", JSON.toJSONString(message));
    }
}
