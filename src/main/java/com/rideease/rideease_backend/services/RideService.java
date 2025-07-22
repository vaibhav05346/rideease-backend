package com.rideease.rideease_backend.services;


import com.rideease.rideease_backend.common.RideStatus;
import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.entity.UserData;
import com.rideease.rideease_backend.repository.RideRepository;
import com.rideease.rideease_backend.repository.UserDataRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserDataRepository userDataRepository;
    private final RabbitMQProducer rabbitMQProducer;

    public RideService(RideRepository rideRepository,UserDataRepository userDataRepository,RabbitMQProducer rabbitMQProducer)
    {
        this.rideRepository=rideRepository;
        this.userDataRepository=userDataRepository;
        this.rabbitMQProducer=rabbitMQProducer;
    }

    public Ride bookRide(Ride ride) throws Exception {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        UserData user = userDataRepository.findByUserName(userName).orElseThrow(()->new RuntimeException("User not found"));
        ride.setUserData(user);
        ride.setStatus(RideStatus.BOOKED.toString());
        ride.setRideBookTime(LocalDateTime.now());
        Ride savedRide = rideRepository.save(ride);
        if(savedRide.getId() != null){
            rabbitMQProducer.sendEmailNotification("EMAIL: Ride has been booked successfully");
            rabbitMQProducer.sendSmsNotification("SMS: Ride has been booked successfully");
            return savedRide;
        }
        else {
            throw new RuntimeException("Ride could not be booked due to some reason");
        }
    }

    public List<Ride> getMyRide() {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        return rideRepository.findByUserName(userName);
    }

    public Ride cancelRide() {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Ride> ride = rideRepository.findLatestActiveBookedRide(userName).getFirst();
        if(ride.isPresent()){
            ride.get().setStatus(RideStatus.CANCEL.toString());
            ride.get().setRideCancelTime(LocalDateTime.now());
            return rideRepository.save(ride.get());
        }
        else{
            throw new RuntimeException("No Active ride for user");
        }
    }
}
