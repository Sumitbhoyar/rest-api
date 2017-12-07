package com.hr.recruitment.web.rest.errors;

import com.hr.recruitment.web.rest.util.HeaderUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Duplicate Entry not allowed";
        return ResponseEntity.unprocessableEntity().headers(HeaderUtil.createFailureAlert("", "", bodyOfResponse)).build();
    }
}