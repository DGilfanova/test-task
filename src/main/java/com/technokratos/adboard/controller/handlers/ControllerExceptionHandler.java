package com.technokratos.adboard.controller.handlers;

import java.util.Collections;

import com.technokratos.adboard.dto.response.ErrorResponse;
import com.technokratos.adboard.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .errors(Collections.singletonList(
                        ErrorResponse.ErrorDto.builder()
                                .exception(exception.getClass().getCanonicalName())
                                .message(exception.getMessage())
                                .build()))
                .build();
        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }
}
