package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;

public class MalformedUrlException extends CustomException {

    private static final long serialVersionUID = 1L;

    public MalformedUrlException (ErrorCode errorCode) {
        super(errorCode);
    }

    public MalformedUrlException (String message) {
        super(message, ErrorCode.MALFORMED_URL_EXCEPTION);
    }

    public MalformedUrlException() {
        super(ErrorCode.MALFORMED_URL_EXCEPTION);
    }
}
