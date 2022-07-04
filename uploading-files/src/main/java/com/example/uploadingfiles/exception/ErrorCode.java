package com.example.uploadingfiles.exception;

public enum ErrorCode {
    FILEINFO_NOT_FOUND( 400,"파일 정보가 존재하지 않습니다."), FILE_NOT_FOUND(400, "파일이 존재하지 않습니다."), CANNOT_STORE_FILE(400, "파일을 저장할 수 없습니다.");
    private final String message;

    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {return status;}

    public String getMessage() {return message;}
}
