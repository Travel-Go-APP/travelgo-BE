package com.travelgo.backend.auth.exception;


import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
