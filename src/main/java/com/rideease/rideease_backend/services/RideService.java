package com.rideease.rideease_backend.services;


import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.repository.RideRepository;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository)
    {
        this.rideRepository=rideRepository;
    }

    public Ride bookRide(Ride ride)
    {
        System.out.println("RideModel booked from " + ride.getSource() + " to " + ride.getDestination() + " for " + ride.getRiderName());
        return rideRepository.save(ride);
    }
}
