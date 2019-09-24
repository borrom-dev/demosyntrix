package com.angkorsuntrix.demosynctrix.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final HttpEntity handleNotFoundException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity.badRequest().build();
    }

}
