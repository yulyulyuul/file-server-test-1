package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;

public class CannotStoreFileException extends CustomException {

    private static final long serialVersionUID = 1L;

    public CannotStoreFileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CannotStoreFileException(String message) {
        super(message, ErrorCode.CANNOT_STORE_FILE);
    }

}
