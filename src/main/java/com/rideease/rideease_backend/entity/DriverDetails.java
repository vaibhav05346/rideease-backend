package com.rideease.rideease_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "driver_details")
@Data
public class DriverDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String driverName;

    private String driverPhoneNumber;

    private Integer rating;

    private Boolean isAvailable;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private VehicleDetails vehicleDetails;
}
