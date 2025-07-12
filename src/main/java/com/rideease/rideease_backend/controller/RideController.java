package com.rideease.rideease_backend.controller;


import com.rideease.rideease_backend.Model.Ride;
import com.rideease.rideease_backend.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService)
    {
        this.rideService=rideService;
    }

    @PostMapping("/book")
    public String bookRide(@RequestBody Ride ride){
        rideService.bookRide(ride);
        return "Ride booked successfully";
    }
}
