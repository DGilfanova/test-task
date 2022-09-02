package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class UserUnavailableOperationException extends BaseException {

    public UserUnavailableOperationException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
