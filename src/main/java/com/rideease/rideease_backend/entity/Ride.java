package com.rideease.rideease_backend.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "source")
    private String source;

    @Column(name= "destination")
    private String destination;

    @Column(name="riderName")
    private String riderName;

    @Column(name="status")
    private String status;

    @Column(name="pickupTime")
    private LocalDateTime pickupTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData userData;
}
