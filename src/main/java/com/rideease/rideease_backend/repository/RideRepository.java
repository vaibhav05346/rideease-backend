package com.rideease.rideease_backend.repository;


import com.rideease.rideease_backend.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride,Integer> {
}
