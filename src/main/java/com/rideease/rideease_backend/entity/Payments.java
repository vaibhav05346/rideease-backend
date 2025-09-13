package com.rideease.rideease_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private LocalDateTime timeStamp;
    private String status;

    @OneToOne(mappedBy = "payments")
    @JsonIgnore
    private Ride ride;
}
