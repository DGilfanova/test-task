package com.technokratos.adboard.controller.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.technokratos.adboard.dto.response.ErrorResponse;
import com.technokratos.adboard.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(handleValidationException(exception.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(handleValidationException(exception.getBindingResult()));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorResponse response = ErrorResponse.builder()
            .errors(Collections.singletonList(
                ErrorResponse.ErrorDto.builder()
                    .exception(exception.getClass().getCanonicalName())
                    .message(exception.getMessage())
                    .build()))
            .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception) {
        ErrorResponse response = ErrorResponse.builder()
            .errors(Collections.singletonList(
                ErrorResponse.ErrorDto.builder()
                    .exception(exception.getClass().getCanonicalName())
                    .message(exception.getMessage())
                    .build()))
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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

    @MessageExceptionHandler
    @SendTo("/user/messages/error")
    public ErrorResponse handleMessageBaseException(BaseException exception) {
        return ErrorResponse.builder()
            .errors(Collections.singletonList(
                ErrorResponse.ErrorDto.builder()
                    .exception(exception.getClass().getCanonicalName())
                    .message(exception.getMessage())
                    .build()))
            .build();
    }

    @MessageExceptionHandler
    @SendTo("/user/messages/error")
    public ErrorResponse handleMessageValidException(
        org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException ex) {
        return handleValidationException(ex.getBindingResult());
    }

    private ErrorResponse handleValidationException(BindingResult bindingResult) {
        if (Objects.isNull(bindingResult)) {
            return null;
        }

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
        return ErrorResponse.builder()
                .errors(errors).build();
    }
}
