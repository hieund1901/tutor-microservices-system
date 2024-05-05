package com.microservices.projectfinal.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandleExceptions extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<Object> response(ResponseException exception) {
        return ResponseHandler.generateResponse(exception.getMessage(), exception.getHttpStatus(), null);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> responseAuth(AuthenticationException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, null);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.joining(", "));
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseHandler.generateResponse(errorMessage,  HttpStatus.BAD_REQUEST, validationList);
    }
}
