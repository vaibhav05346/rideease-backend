package com.rideease.rideease_backend.services;

import com.rideease.rideease_backend.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate=rabbitTemplate;
    }


    public void sendEmailNotification(String email){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.EMAIL_ROUTING_KEY,email);
    }

    public void sendSmsNotification(String sms){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.SMS_ROUTING_KEY,sms);
    }
}
