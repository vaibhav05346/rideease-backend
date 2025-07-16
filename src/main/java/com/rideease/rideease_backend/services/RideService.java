package com.rideease.rideease_backend.services;


import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.entity.UserData;
import com.rideease.rideease_backend.repository.RideRepository;
import com.rideease.rideease_backend.repository.UserDataRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserDataRepository userDataRepository;

    public RideService(RideRepository rideRepository,UserDataRepository userDataRepository)
    {
        this.rideRepository=rideRepository;
        this.userDataRepository=userDataRepository;
    }

    public Ride bookRide(Ride ride) throws Exception {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        UserData user = userDataRepository.findByUserName(userName).orElseThrow(()->new RuntimeException("User not found"));
        ride.setUserData(user);
        ride.setStatus("Ride Booked Successfully");
        ride.setPickupTime(LocalDateTime.now());
        return rideRepository.save(ride);
    }
}
