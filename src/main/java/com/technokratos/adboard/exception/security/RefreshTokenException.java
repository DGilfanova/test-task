package com.technokratos.adboard.exception.security;

import com.technokratos.adboard.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RefreshTokenException extends BaseException {

    public RefreshTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
