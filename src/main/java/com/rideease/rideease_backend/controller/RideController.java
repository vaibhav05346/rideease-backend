package com.rideease.rideease_backend.controller;


import com.rideease.rideease_backend.common.ApiResponse;
import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.model.RideModel;
import com.rideease.rideease_backend.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rideease.rideease_backend.common.Mapper.rideEntityToRideModel;

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
    public ApiResponse<RideModel> bookRide(@RequestBody Ride rideEntity){
        RideModel rideModel = rideEntityToRideModel(rideService.bookRide(rideEntity));
        return new ApiResponse<>("success","Ride booked successfully",rideModel);
    }
}
