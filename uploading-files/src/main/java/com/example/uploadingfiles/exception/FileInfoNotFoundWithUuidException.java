package com.example.uploadingfiles.exception;


public class FileInfoNotFoundWithUuidException extends RuntimeException {

    public FileInfoNotFoundWithUuidException (String message) {
            super(message);
        }

    public FileInfoNotFoundWithUuidException (String message, Throwable cause) {
            super(message, cause);
        }

    }
