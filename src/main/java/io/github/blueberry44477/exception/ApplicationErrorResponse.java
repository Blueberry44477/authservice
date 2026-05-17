package io.github.blueberry44477.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApplicationErrorResponse {
    private String message;
    private Object details;
    private String errorCode;

    public ApplicationErrorResponse(String message, Object details) {
        this.message = message;
        this.details = details;
    }

    public ApplicationErrorResponse(String message) {
        this.message = message;
    }

    public ApplicationErrorResponse(Exception ex) {
        this.message = ex.getMessage();
    }
}