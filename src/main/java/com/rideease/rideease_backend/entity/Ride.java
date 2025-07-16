package com.rideease.rideease_backend.entity;


import jakarta.persistence.*;
import lombok.Data;

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
}
