package com.rideease.rideease_backend.model;


import com.rideease.rideease_backend.entity.VehicleDetails;
import com.rideease.rideease_backend.entity.DriverDetails;
import com.rideease.rideease_backend.entity.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class RideModel {
    private String source;
    private String destination;
    private LocalDateTime rideBookTime;
    private LocalDateTime rideCancelTime;
    private LocalDateTime rideCompleteTime;
    private String status;
    private Double price;
    private DriverDetails driverDetails;
    private VehicleDetails vehicleDetails;
    private UserData userData;
}
