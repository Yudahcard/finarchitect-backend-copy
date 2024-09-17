package com.finEasy.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ecobank.remittancecomplianceservice.models.ErrorResponse;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BAD_REQUEST = "BAD_REQUEST";

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request)
    {
        List<String> details = ex.getConstraintViolations()
                .parallelStream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        ErrorResponse error = new ErrorResponse("Field Validation Failed", details,HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException (
            ConstraintViolationException ex,
            WebRequest request)
    {
        ErrorResponse error = new ErrorResponse("Token has expiration, request for a new one.", Collections.singletonList(ex.getMessage()), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ErrorResponse apiError = new ErrorResponse("Validation Failed", errors, HttpStatus.BAD_REQUEST.value());
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse apiError = new ErrorResponse("Validation Failed", Arrays.asList(ex.getMessage()), HttpStatus.BAD_REQUEST.value());
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }


    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<Object> handleUnrecognizedPropertyException (UnrecognizedPropertyException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse apiError = new ErrorResponse("Unrecognized Field Value", Arrays.asList(ex.getMessage()), HttpStatus.BAD_REQUEST.value());
        return handleExceptionInternal(ex, apiError, headers,status, request);
    }



}
