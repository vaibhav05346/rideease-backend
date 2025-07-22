package com.rideease.rideease_backend.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String SMS_QUEUE = "sms_queue";
    public static final String EMAIL_QUEUE = "email_queue";
    public static final String SMS_ROUTING_KEY = "sms_routing_key";
    public static final String EMAIL_ROUTING_KEY = "email_routing_key";
    public static final String EXCHANGE = "exchange";


    @Bean
    public Queue smsQueue(){
        return new Queue(SMS_QUEUE);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding emailBindingBuilder(){
        return BindingBuilder.bind(emailQueue()).to(topicExchange()).with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Binding smsBindingBuilder(){
        return BindingBuilder.bind(smsQueue()).to(topicExchange()).with(SMS_ROUTING_KEY);
    }
}
