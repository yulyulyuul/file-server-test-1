package com.example.uploadingfiles.exception;

public enum ErrorCode {
    FILEINFO_NOT_FOUND( 400,"파일 정보가 존재하지 않습니다."),
    FILE_NOT_FOUND(400, "파일이 존재하지 않습니다."),
    CANNOT_STORE_FILE(400, "파일을 저장할 수 없습니다."),
    INVALID_FILE_PATH(400, "파일 경로가 올바르지 않습니다."),
    UNABLE_TO_CREATE_DIRECTORY(400, "파일 디렉토리를 생성할 수 없습니다."), 
    MALFORMED_URL_EXCEPTION(400, "URL 생성 오류입니다."),
    FILE_TYPE_IS_NOT_IMAGE(400, "이미지 파일이 아닙니다.");
    private final String message;

    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {return status;}

    public String getMessage() {return message;}
}
