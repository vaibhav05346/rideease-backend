package com.rideease.rideease_backend.services;


import com.rideease.rideease_backend.entity.Payments;
import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.repository.PaymentsRepository;
import com.rideease.rideease_backend.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;
    private final RideRepository rideRepository;
    private final RabbitMQProducer rabbitMQProducer;


    public PaymentsService(RabbitMQProducer rabbitMQProducer,RideRepository rideRepository,PaymentsRepository paymentsRepository){
        this.rideRepository=rideRepository;
        this.paymentsRepository=paymentsRepository;
        this.rabbitMQProducer=rabbitMQProducer;
    }

    public List<Payments> findAllPayments() {
        return paymentsRepository.findAll();
    }

    public Payments completePayment(Integer rideId) {
        Payments payments = paymentsRepository.findByRideId(rideId).orElse(null);
        if (payments == null) {
            throw new IllegalArgumentException("Ride not found with ID: " + rideId);
        }
        payments.setStatus("Completed");
        payments.setTimeStamp(LocalDateTime.now());
        Payments savedPayments = paymentsRepository.save(payments);
        sendNotification("EMAIL: payment completed successfully","SMS: payment completed successfully");
        return savedPayments;

    }

    public Payments findPaymentById(Integer id) {
        return paymentsRepository.findById(id).orElse(null);
    }

    private void sendNotification(String email, String sms) {
        rabbitMQProducer.sendEmailNotification(email);
        rabbitMQProducer.sendSmsNotification(sms);
    }
}
