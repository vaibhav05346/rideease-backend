package com.rideease.rideease_backend.common;

import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.model.RideModel;
import com.rideease.rideease_backend.model.RideRequest;

import java.util.List;

public class Mapper {

    public static RideModel rideEntityToRideModel(Ride rideEntity)
    {
        return new RideModel(rideEntity.getSource(),rideEntity.getDestination(),rideEntity.getRideBookTime(),
                rideEntity.getRideCancelTime(), rideEntity.getRideCompleteTime(),rideEntity.getStatus(),rideEntity.getPayments().getAmount(),
                rideEntity.getDriverDetails(),rideEntity.getVehicleDetails(),rideEntity.getUserData());
    }

    public static List<RideModel> listRideEntityToListRideModel(List<Ride> rides)
    {
        return rides.stream().map(Mapper::rideEntityToRideModel).toList();
    }

    public static Ride rideRequestToRideEntity(RideRequest request)
    {
        return new Ride(request.getSource(),request.getDestination(),request.getRideType());
    }
}
