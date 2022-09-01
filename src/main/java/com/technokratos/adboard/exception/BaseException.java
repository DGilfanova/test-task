package com.technokratos.adboard.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
public class BaseException extends RuntimeException {

    private HttpStatus httpStatus;

    public BaseException(HttpStatus httpStatus) {
        super();
        setHttpStatus(httpStatus);
    }

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        setHttpStatus(httpStatus);
    }

    public BaseException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        setHttpStatus(httpStatus);
    }
}
