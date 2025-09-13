package com.rideease.rideease_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehicle_details")
@Data
public class VehicleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String vehicleName;

    private String vehicleColour;

    private String vehicleNumber;

    private String vehicleType;
}
