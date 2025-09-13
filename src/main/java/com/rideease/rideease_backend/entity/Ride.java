package com.rideease.rideease_backend.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "source")
    private String source;

    @Column(name= "destination")
    private String destination;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private DriverDetails driverDetails;

    @Column(name="status")
    private String status;

    @Column(name="rideBookTime")
    private LocalDateTime rideBookTime;

    @Column(name = "rideCancelTime")
    private LocalDateTime rideCancelTime;

    @Column(name = "rideCompleteTime")
    private LocalDateTime rideCompleteTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData userData;

    @Column(name = "rideType")
    private String rideType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payments_id")
    private Payments payments;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "driverLocation")
    private Double driverLocation;

    @ManyToOne
    private VehicleDetails vehicleDetails;


    public Ride(String source,String destination,String rideType)
    {
        this.source=source;
        this.destination=destination;
        this.rideType=rideType;
    }
}
