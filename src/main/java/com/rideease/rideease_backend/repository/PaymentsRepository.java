package com.rideease.rideease_backend.repository;

import com.rideease.rideease_backend.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments,Integer> {

    Optional<Payments> findByRideId(Integer rideId);

    List<Payments> findAll();
}
