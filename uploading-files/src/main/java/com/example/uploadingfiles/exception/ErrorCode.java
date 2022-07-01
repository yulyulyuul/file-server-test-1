package com.example.uploadingfiles.exception;

public enum ErrorCode {
    ;
    private final String message;

    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {return status;}

    public String getMessage() {return message;}
}
