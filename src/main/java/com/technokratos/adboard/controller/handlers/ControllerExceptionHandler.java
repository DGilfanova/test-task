package com.technokratos.adboard.controller.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.technokratos.adboard.dto.response.ErrorResponse;
import com.technokratos.adboard.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
        MethodArgumentNotValidException exception) {
        return handleValidationException(exception.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        return handleValidationException(exception.getBindingResult());
    }

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

    private ResponseEntity<ErrorResponse> handleValidationException(BindingResult bindingResult) {
        List<ErrorResponse.ErrorDto> errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach((error) -> {

            String errorMessage = error.getDefaultMessage();

            ErrorResponse.ErrorDto errorDto = ErrorResponse.ErrorDto.builder()
                .message(errorMessage)
                .build();

            String objectName;
            if (error instanceof FieldError) {
                objectName = ((FieldError) error).getField();
            } else {
                objectName = error.getObjectName();
            }
            errorDto.setObject(objectName);
            errors.add(errorDto);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
                .errors(errors).build());
    }
}
