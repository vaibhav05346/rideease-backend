package com.rideease.rideease_backend.common;

import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.model.RideModel;

public class Mapper {

    private final Ride rideEntity;
    private final RideModel rideModel;

    public Mapper(Ride rideEntity,RideModel rideModel)
    {
        this.rideEntity=rideEntity;
        this.rideModel=rideModel;
    }

    public static RideModel rideEntityToRideModel(Ride rideEntity)
    {
        return new RideModel(rideEntity.getSource(),rideEntity.getDestination(),rideEntity.getRiderName());
    }
}
