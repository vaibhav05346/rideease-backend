package com.rideease.rideease_backend.controller;


import com.rideease.rideease_backend.common.CustomApiResponse;
import com.rideease.rideease_backend.exception.RideProcessException;
import com.rideease.rideease_backend.model.RideModel;
import com.rideease.rideease_backend.model.RideRequest;
import com.rideease.rideease_backend.repository.UserDataRepository;
import com.rideease.rideease_backend.services.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rideease.rideease_backend.common.Mapper.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    private final UserDataRepository userDataRepository;


    @Autowired
    public RideController(RideService rideService,UserDataRepository userDataRepository)
    {
        this.rideService=rideService;
        this.userDataRepository=userDataRepository;
    }

    @Operation(summary = "Book a new ride", description = "Creates a new ride booking for the authenticated user",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ride booked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/book")
    public CustomApiResponse<RideModel> bookRide(@Validated @RequestBody RideRequest rideRequest) throws RideProcessException {
        RideModel rideModel = rideEntityToRideModel(rideService.bookRide(rideRequestToRideEntity(rideRequest)));
        return new CustomApiResponse<RideModel>("success","Ride booked successfully",rideModel);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),summary = "Get all rides", description = "get all rides information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch successfull"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/myRides")
    public CustomApiResponse<List<RideModel>> fetchUserRides()
    {
        List<RideModel> rideModel = listRideEntityToListRideModel(rideService.getMyRide());
        return new CustomApiResponse<List<RideModel>>("success","My booked Rides",rideModel);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),summary = "Cancel existing active ride", description = "Cancels the ride which is active for authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ride booked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/cancel")
    public CustomApiResponse<RideModel> cancelRide() throws RideProcessException
    {
        RideModel rideModel = rideEntityToRideModel(rideService.cancelRide());
        return new CustomApiResponse<RideModel>("success","My ride cancelled successfully",rideModel);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"),summary = "Complete active ride", description = "Completes the last active ride for authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ride booked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/completeRide")
    public CustomApiResponse<RideModel> completeRide() throws Exception {
        RideModel rideModel = rideEntityToRideModel(rideService.completeRide());
        return new CustomApiResponse<RideModel>("Successfull","Ride Completed Successfully",rideModel);
    }
}
