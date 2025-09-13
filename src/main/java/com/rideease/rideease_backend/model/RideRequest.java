package com.rideease.rideease_backend.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotNull;

@Component
@Data
public class RideRequest {

    @Schema(description = "Pickup location name", example = "Connaught Place")
    @NotBlank(message = "pickup location is required")
    private String source;

    @Schema(description = "Drop location name", example = "Noida")
    @NotBlank(message = "drop location is required")
    private String destination;

    @Schema(description = "ride type name", example = "Cab/Auto")
    @NotBlank(message = "ride type is required")
    private String rideType;
}
