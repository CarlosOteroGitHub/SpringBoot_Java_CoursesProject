package com.backend.answers.auxiliar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    private static ApiExceptionHandler instance = null;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(Exception e) {
        ApiExceptionResponse response = new ApiExceptionResponse("Recurso no encontrado", "Error-01", e.getMessage());
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }
    
    public static ApiExceptionHandler getInstance() {
        if (instance == null) {
            instance = new ApiExceptionHandler();
        }
        return instance;
    }
}