package com.travelgo.backend.domain.user.exception;

public class GeoCodingException extends RuntimeException{
    public GeoCodingException(String message){
        super(message);
    }
}
