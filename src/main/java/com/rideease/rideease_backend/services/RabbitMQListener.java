package com.rideease.rideease_backend.services;

import com.rideease.rideease_backend.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void emailQueueConsumer(String email){
        System.out.println("EMAIL: Ride has been booked successfully");
    }

    @RabbitListener(queues = RabbitMQConfig.SMS_QUEUE)
    public void smsQueueConsumer(String sms)
    {
        System.out.println("SMS: Ride has been booked successfully");
    }
}
