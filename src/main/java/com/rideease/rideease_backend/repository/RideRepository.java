package com.rideease.rideease_backend.repository;


import com.rideease.rideease_backend.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride,Integer> {

    @Query(value = "select rd from Ride rd join UserData ud ON rd.userData.id=ud.id where ud.userName = :userName")
    List<Ride> findByUserName(@Param("userName") String userName);

    @Query(value = "select rd from Ride rd join UserData ud ON rd.userData.id=ud.id where ud.userName = :userName order by rd.rideBookTime desc limit 1")
    Optional<Ride> findLatestActiveBookedRide(@Param("userName") String userName);
}
