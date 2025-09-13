package com.rideease.rideease_backend.controller;

import com.rideease.rideease_backend.common.CustomApiResponse;
import com.rideease.rideease_backend.entity.Payments;
import com.rideease.rideease_backend.services.PaymentsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    
    private final PaymentsService paymentsService;
    
    public PaymentsController(PaymentsService paymentsService){
        this.paymentsService = paymentsService;
    }


    @GetMapping("/getAllPayments")
    public CustomApiResponse<List<Payments>> getAllPayments()
    {
        return new CustomApiResponse<List<Payments>>("Successfull","All payments fetched successfully", paymentsService.findAllPayments());
    }

    @GetMapping("/getPayment/{id}")
    public CustomApiResponse<Payments> getPaymentsById(@PathVariable Integer id)
    {
        return new CustomApiResponse<Payments>("Successfull","payments with id: " + id +" fetched successfully", paymentsService.findPaymentById(id));
    }


    @PostMapping("/doPayments/{id}")
    public CustomApiResponse<Payments> doPayments(@PathVariable Integer id)
    {
        Payments savedPayments = paymentsService.completePayment(id);
        return new CustomApiResponse<Payments>("Successfull","Payment Done successfully",savedPayments);
    }
}
