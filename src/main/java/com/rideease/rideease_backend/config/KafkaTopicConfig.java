package com.rideease.rideease_backend.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.rideBooked}")
    private String rideBookTopic;

    @Value("${kafka.topic.rideCompleted}")
    private String rideCompleteTopic;


    @Bean
    public NewTopic rideBookTopic(){
        return TopicBuilder.name(rideBookTopic)
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic rideCompleteTopic(){
        return TopicBuilder.name(rideCompleteTopic)
                .partitions(3)
                .build();
    }
}
