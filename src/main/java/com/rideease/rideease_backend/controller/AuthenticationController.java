package com.rideease.rideease_backend.controller;

import com.rideease.rideease_backend.common.ApiResponse;
import com.rideease.rideease_backend.model.UserData;
import com.rideease.rideease_backend.repository.UserRepository;
import com.rideease.rideease_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(UserRepository userRepository,JwtUtils jwtUtils)
    {
        this.userRepository=userRepository;
        this.jwtUtils=jwtUtils;
    }

    @PostMapping("/signup")
    public ApiResponse<UserData> signUp(@RequestBody UserData userData)
    {
        Optional<UserData> existingUser = userRepository.findByUserName(userData.getUserName());
        if(existingUser.isPresent()){
            throw new RuntimeException("UserData Already Exists");
        }
        UserData savedUserData = userRepository.save(userData);
        return new ApiResponse<>("success","userData saved successfully", savedUserData);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserData loginUserData){
        Optional<UserData> userOptional = userRepository.findByUserName(loginUserData.getUserName());
        if(userOptional.isEmpty())
        {
            throw new RuntimeException("Invalid Username");
        }
        UserData userData = userOptional.get();
        if(!userData.getPassword().equals(loginUserData.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String token = jwtUtils.generateToken(loginUserData.getUserName());
        return new ApiResponse<>("Success","token generated successfully",token);
    }
}
