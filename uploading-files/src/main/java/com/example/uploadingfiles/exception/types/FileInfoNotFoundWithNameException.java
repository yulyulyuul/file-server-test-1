package com.example.uploadingfiles.exception.types;

public class FileInfoNotFoundWithNameException extends RuntimeException {

    public FileInfoNotFoundWithNameException (String message) {
        super(message);
    }

    public FileInfoNotFoundWithNameException (String message, Throwable cause) {
        super(message, cause);
    }
}
