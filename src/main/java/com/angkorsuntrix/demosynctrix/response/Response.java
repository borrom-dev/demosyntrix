package com.angkorsuntrix.demosynctrix.response;

import com.angkorsuntrix.demosynctrix.utils.DateUtils;

public class Response<T> {

    private Status status;
    private Object error;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public void addErrorMsgToResponse(String errorMsg, Exception ex) {
        ResponseError error = new ResponseError();
        error.setDetails(errorMsg);
        error.setMessage(ex.getMessage());
        error.setTimestamp(DateUtils.today());
        setError(error);
    }

    public static <T> Response<T> duplicateEntity() {
        final Response<T> response = new Response<>();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }

    public enum Status {
        OK, BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS, ACCESS_DENIED, NOT_FOUND, DUPLICATE_ENTITY
    }
}
