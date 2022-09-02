package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends BaseException {

    public FileStorageException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
