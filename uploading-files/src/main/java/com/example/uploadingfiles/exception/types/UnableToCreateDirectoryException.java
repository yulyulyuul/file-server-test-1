package com.example.uploadingfiles.exception.types;

public class UnableToCreateDirectoryException extends RuntimeException {

     public UnableToCreateDirectoryException (String message) {
            super(message);
        }

    public UnableToCreateDirectoryException (String message, Throwable cause) {
            super(message, cause);
        }

    }

