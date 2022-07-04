package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;

public class InvalidFilePathException extends CustomException {

    private static final long serialVersionUID = 1L;

    public InvalidFilePathException (ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidFilePathException (String message) {
        super(message, ErrorCode.INVALID_FILE_PATH);
    }

    public InvalidFilePathException() {
        super(ErrorCode.INVALID_FILE_PATH);
    }
}
