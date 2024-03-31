package com.orana.appstockexchange.config;

import com.orana.appstockexchange.exception.DuplicateRecordException;
import com.orana.appstockexchange.exception.NoRecordFoundException;
import com.orana.appstockexchange.model.dto.ResponseModel;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String MESSAGE_INVALID_REQUEST_CONTENT = "Invalid request content.";
    public static final String MESSAGE_USER_NOT_FOUND = "User not found.";
    public static final String MESSAGE_STOCK_NOT_FOUND = "Stock not found.";
    public static final String MESSAGE_STOCK_EXCHANGE_NOT_FOUND = "Stock exchange not found.";

    @ExceptionHandler(value = {UsernameNotFoundException.class, NoRecordFoundException.class})
    protected ResponseEntity<Object> handleNoRecordFoundException(Exception ex) {
        return new ResponseEntity<>(new ResponseModel<>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateRecordException.class})
    protected ResponseEntity<Object> handleDuplicateRecordException(Exception ex) {
        return new ResponseEntity<>(new ResponseModel<>(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(Exception ex) {
        return new ResponseEntity<>(new ResponseModel<>(MESSAGE_INVALID_REQUEST_CONTENT, null), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleNoRecordFoundException(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ResponseModel<>(ex.getBody().getDetail(), null), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ResponseModel<>(MESSAGE_INVALID_REQUEST_CONTENT, null), HttpStatus.BAD_REQUEST);
    }
}
