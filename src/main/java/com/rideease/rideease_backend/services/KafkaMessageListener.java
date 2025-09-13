package com.rideease.rideease_backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rideease.rideease_backend.entity.Payments;
import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.repository.RideRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaMessageListener {

    private final PaymentsService paymentsService;
    private final RideRepository rideRepository;

    public KafkaMessageListener(RideRepository rideRepository, PaymentsService paymentsService)
    {
        this.rideRepository=rideRepository;
        this.paymentsService=paymentsService;
    }
//
//    @KafkaListener(topics ="${kafka.topic.rideBooked}" , groupId = "rideBook-consumer-group")
//    public void rideBookedMessageConsume(String message) throws Exception {
//        System.out.println(message);
//    }

//    @KafkaListener(topics = "${kafka.topic.rideCompleted}", groupId = "rideComplete-group-test")
//    public void rideCompletedMessageConsume(String message) {
//        System.out.println("🎯 MESSAGE RECEIVED IN LISTENER ===> " + message);
//    }


    @KafkaListener(topics = "${kafka.topic.rideCompleted}", groupId = "rideComplete-group-test")
    public void rideCompletedMessageConsume(String message) throws Exception {
        System.out.println("came into listener"+ message);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Ride ride = objectMapper.readValue(message, Ride.class);
        try{
            Payments payments =paymentsService.completePayment(ride.getId());
            ride.setPayments(payments);
            rideRepository.save(ride);

        } catch (Exception e) {
            throw new RuntimeException("Unable to process payment",e);
        }
    }
}
