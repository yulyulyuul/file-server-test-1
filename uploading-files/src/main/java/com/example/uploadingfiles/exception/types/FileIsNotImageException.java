package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;

public class FileIsNotImageException extends CustomException {

    private static final long serialVersionUID = 1L;

    public FileIsNotImageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FileIsNotImageException (String message) {
        super(message, ErrorCode.FILE_TYPE_IS_NOT_IMAGE);
    }

    public FileIsNotImageException () {
        super(ErrorCode.FILE_TYPE_IS_NOT_IMAGE);
    }
}

