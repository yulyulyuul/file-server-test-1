package com.example.uploadingfiles.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
