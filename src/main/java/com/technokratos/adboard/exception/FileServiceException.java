package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class FileServiceException extends BaseException {

    public FileServiceException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }
}
