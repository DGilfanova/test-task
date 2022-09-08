package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class AdNotFoundException extends BaseException {

    public AdNotFoundException() {
        super("Advertisement not found. Maybe it's not active anymore", HttpStatus.NOT_FOUND);
    }
}
