package com.microservices.projectfinal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;
}