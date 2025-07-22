package com.rideease.rideease_backend.controller;

import com.rideease.rideease_backend.common.ApiResponse;
import com.rideease.rideease_backend.entity.UserData;
import com.rideease.rideease_backend.utils.JwtUtils;
import com.rideease.rideease_backend.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserDataService userDataService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(UserDataService userDataService, JwtUtils jwtUtils)
    {
        this.userDataService = userDataService;
        this.jwtUtils=jwtUtils;
    }

    @PostMapping("/signup")
    public ApiResponse<UserData> signUp(@RequestBody UserData userData)
    {
        UserData savedUserData = userDataService.signUpUser(userData);
        return new ApiResponse<>("success","userData saved successfully", savedUserData);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserData loginUserData){
        String token = userDataService.loginUser(loginUserData);
        return new ApiResponse<>("Success","token generated successfully",token);
    }
}
