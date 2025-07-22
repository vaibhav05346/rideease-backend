package com.rideease.rideease_backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class RideModel {
    private String source;
    private String destination;
    private String riderName;
    private LocalDateTime rideBookTime;
    private LocalDateTime rideCancelTime;
    private String status;
}
