package com.bdxmx.miniprogram.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bdxmx.miniprogram.prop.RabbitMQProperty;
import com.ik.crm.commons.constant.MonitorConstants;
import com.ik.crm.commons.util.StringUtils;

@Configuration
public class RabbitMQConfig implements MonitorConstants {

    @Autowired
    private RabbitMQProperty rabbitMQProperty;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        if (StringUtils.isBlank(rabbitMQProperty.getPort())) {
            connectionFactory.setAddresses(rabbitMQProperty.getHost());
        } else {
            connectionFactory.setAddresses(rabbitMQProperty.getHost() + ":" + rabbitMQProperty.getPort());
        }
        connectionFactory.setUsername(rabbitMQProperty.getUsername());
        connectionFactory.setPassword(rabbitMQProperty.getPassword());
        connectionFactory.setVirtualHost(rabbitMQProperty.getVirtualHost());
        connectionFactory.setPublisherConfirms(true); // 必须要设置
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

}
