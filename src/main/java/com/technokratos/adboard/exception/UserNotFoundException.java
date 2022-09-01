package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
