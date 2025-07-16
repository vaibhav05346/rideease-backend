package com.rideease.rideease_backend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String userName;
    private String password;
    private String role;
}
