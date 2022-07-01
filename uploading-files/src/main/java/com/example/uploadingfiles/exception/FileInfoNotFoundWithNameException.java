package com.example.uploadingfiles.exception;

public class FileInfoNotFoundWithNameException extends RuntimeException {

    public FileInfoNotFoundWithNameException (String message) {
        super(message);
    }

    public FileInfoNotFoundWithNameException (String message, Throwable cause) {
        super(message, cause);
    }
}
