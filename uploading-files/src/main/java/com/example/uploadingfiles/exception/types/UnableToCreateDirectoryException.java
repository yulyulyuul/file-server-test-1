package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;

public class UnableToCreateDirectoryException extends CustomException {

    private static final long serialVersionUID = 1L;

    public UnableToCreateDirectoryException (ErrorCode errorCode) {
        super(errorCode);
    }

    public UnableToCreateDirectoryException (String message) {
        super(message, ErrorCode.UNABLE_TO_CREATE_DIRECTORY);
    }

    public UnableToCreateDirectoryException() {
        super(ErrorCode.UNABLE_TO_CREATE_DIRECTORY);
    }
}

