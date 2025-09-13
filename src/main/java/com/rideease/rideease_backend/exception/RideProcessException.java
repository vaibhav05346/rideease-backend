package com.rideease.rideease_backend.exception;

public class RideProcessException extends Exception{

    public RideProcessException(String message) {
        super(message);
    }

    public RideProcessException(String message,Throwable cause){
        super(message, cause);
    }

}
