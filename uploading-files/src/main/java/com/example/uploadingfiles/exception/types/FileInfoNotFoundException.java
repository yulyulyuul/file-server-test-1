package com.example.uploadingfiles.exception.types;

import com.example.uploadingfiles.exception.CustomException;
import com.example.uploadingfiles.exception.ErrorCode;

public class FileInfoNotFoundException extends CustomException {

    private static final long serialVersionUID = 1L;

    public FileInfoNotFoundException (ErrorCode errorCode) {
        super(errorCode);
    }

    public FileInfoNotFoundException (String message) {
        super(message, ErrorCode.FILEINFO_NOT_FOUND);
    }
}
