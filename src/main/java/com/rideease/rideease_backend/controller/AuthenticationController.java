package com.rideease.rideease_backend.controller;

import com.rideease.rideease_backend.common.CustomApiResponse;
import com.rideease.rideease_backend.entity.UserData;
import com.rideease.rideease_backend.utils.JwtUtils;
import com.rideease.rideease_backend.services.UserDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create new user", description = "Sign up in ride ease to create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SignUp Successfull"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signup")
    public CustomApiResponse<UserData> signUp(@RequestBody UserData userData)
    {
        UserData savedUserData = userDataService.signUpUser(userData);
        return new CustomApiResponse<UserData>("success","userData saved successfully", savedUserData);
    }

    @Operation(summary = "Log in", description = "LogIn in ride ease app")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "LogIn successfull"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public CustomApiResponse<String> login(@RequestBody UserData loginUserData){
        String token = userDataService.loginUser(loginUserData);
        return new CustomApiResponse<String>("Success","token generated successfully",token);
    }
}
