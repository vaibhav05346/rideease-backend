package com.rideease.rideease_backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${kafka.topic.rideBooked}")
    private String rideBookTopic;


    @Value("${kafka.topic.rideCompleted}")
    private String rideCompleteTopic;


    public void sendRideBookEvent(String message)
    {
        LOGGER.info("Sending message to Kafka RideBookTopic: {}", message);
        kafkaTemplate.send(rideBookTopic,message);
    }

    public void sendRideCompleteEvent(String payload){
        LOGGER.info("Sending payload to kafka rideCompleteTopic");
        kafkaTemplate.send(rideCompleteTopic,payload);
    }
}
