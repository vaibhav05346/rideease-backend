package com.rideease.rideease_backend.repository;

import com.rideease.rideease_backend.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData,Integer> {

     Optional<UserData> findByUserName(String userName);
}
