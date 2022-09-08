package com.technokratos.adboard.exception;

import org.springframework.http.HttpStatus;

/**
 * @author d.gilfanova
 */
public class ChatNotFoundException extends BaseException {

    public ChatNotFoundException() {
        super("Chat not found", HttpStatus.NOT_FOUND);
    }
}
