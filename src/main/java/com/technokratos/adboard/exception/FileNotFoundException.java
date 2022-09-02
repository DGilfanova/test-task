package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class FileNotFoundException extends BaseException {

    public FileNotFoundException() {
        super("File not found", HttpStatus.NOT_FOUND);
    }
}
