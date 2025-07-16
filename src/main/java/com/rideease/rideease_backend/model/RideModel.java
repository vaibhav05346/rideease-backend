package com.rideease.rideease_backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RideModel {
    private String source;
    private String destination;
    private String riderName;
}
