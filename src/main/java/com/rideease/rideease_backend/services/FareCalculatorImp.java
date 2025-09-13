package com.rideease.rideease_backend.services;

import org.springframework.stereotype.Service;

@Service
public class FareCalculatorImp implements FareCalculator{

    @Override
    public Double calculateFare(String rideType, Double distanceInKm) {
        double baseFare;
        double ratePerKm;
        switch (rideType.toUpperCase()) {
            case "AUTO":
                baseFare = 30;
                ratePerKm = 8;
                break;
            case "ECONOMY":
                baseFare = 50;
                ratePerKm = 12;
                break;
            case "PREMIUM":
                baseFare = 100;
                ratePerKm = 20;
                break;
            default:
                throw new IllegalArgumentException("Unknown Ride Type");
            }

            return baseFare + (ratePerKm * distanceInKm);
        }

}
