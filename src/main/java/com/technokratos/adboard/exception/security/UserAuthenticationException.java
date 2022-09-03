package com.technokratos.adboard.exception.security;

import com.technokratos.adboard.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserAuthenticationException extends BaseException {

    public UserAuthenticationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
