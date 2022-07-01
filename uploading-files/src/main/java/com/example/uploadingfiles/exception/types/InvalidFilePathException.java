package com.example.uploadingfiles.exception.types;

public class InvalidFilePathException extends RuntimeException {

    public InvalidFilePathException (String message) {
        super(message);
    }

    public InvalidFilePathException(String message, Throwable cause) {
        super(message, cause);
    }
}
