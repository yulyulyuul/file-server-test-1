package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class FileNotFoundException extends CustomException {

    private static final long serialVersionUID = 1L;

    public FileNotFoundException (ErrorCode errorCode) {
        super(errorCode);
    }

    public FileNotFoundException (String message) {
        super(message, ErrorCode.FILE_NOT_FOUND);
    }
}
