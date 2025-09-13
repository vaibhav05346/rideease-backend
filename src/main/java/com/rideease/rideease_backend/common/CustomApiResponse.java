package com.rideease.rideease_backend.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomApiResponse<T> {

    private String status;
    private String message;
    private T data;

}
