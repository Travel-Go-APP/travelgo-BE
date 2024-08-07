package com.travelgo.backend.auth.exception;


import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
