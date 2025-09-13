package com.rideease.rideease_backend.repository;

import com.rideease.rideease_backend.entity.DriverDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DriverRepository extends JpaRepository<DriverDetails,Integer> {


    @Query(value="select driver from DriverDetails driver join VehicleDetails vd on driver.vehicleDetails.id=vd.id where driver.isAvailable=true and vd.vehicleType LIKE %:vehicleType% order by driver.rating desc limit 1")
    DriverDetails findDriverByAvailabilityAndHighestRatingAndRideType(@Param("vehicleType") String vehicleType);

//    @Query(value = "select dd from DriverDetails dd join Ride rd on dd.id = rd.driverDetails.id JOIN UserData ud ON rd.userData.id=ud.id where ud.userName = :userName order by rd.rideBookTime desc limit 1")
//    DriverDetails getDriverAttachedToLatestActiveRide(@Param("userName") String userName);
}
