package com.example.uploadingfiles.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String message;

    private final int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomError> customErrors;

    @Builder
    public ErrorResponse(String message, int status, Errors errors) {
        this.message = message;
        this.status = status;
        this.customErrors = new ArrayList<>();

        if (errors != null) {
            errors.getFieldErrors().forEach(error -> this.customErrors.add(
                    new CustomFieldError(error.getField(), error.getRejectedValue(), error.getDefaultMessage())));
        }

    }

    public void setCustomErrors(List<FieldError> fieldErrors) {
        customErrors = new ArrayList<>();

        fieldErrors.forEach(error -> customErrors.add(
                new CustomFieldError(error.getField(), error.getRejectedValue(), error.getDefaultMessage())));
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public List<CustomError> getCustomErrors() {
        return customErrors;
    }

    public static class CustomError {

    }

    public static class CustomFieldError extends CustomError{

        private final String field;
        private final Object value;
        private final String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }


}
