package com.angkorsuntrix.demosynctrix.exception;

import java.io.IOException;

public class ResponseException {
    private String message;
    private int statusCode;

    public ResponseException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseException(String message) {
        this.message = message;
    }

    public ResponseException() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
