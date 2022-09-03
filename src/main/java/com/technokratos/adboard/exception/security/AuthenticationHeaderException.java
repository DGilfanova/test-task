package com.technokratos.adboard.exception.security;

import com.technokratos.adboard.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AuthenticationHeaderException extends BaseException {

    public AuthenticationHeaderException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
