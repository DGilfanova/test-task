package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class DealNotFoundException extends BaseException {

    public DealNotFoundException() {
        super("Deal not found", HttpStatus.NOT_FOUND);
    }
}
