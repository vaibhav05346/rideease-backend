package com.rideease.rideease_backend.services;


import com.rideease.rideease_backend.Model.Ride;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    public void bookRide(Ride ride)
    {
        System.out.println("Ride booked from " + ride.getSource() + " to " + ride.getDestination() + " for " + ride.getRiderName());
    }
}
