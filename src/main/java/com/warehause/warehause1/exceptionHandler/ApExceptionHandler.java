package com.warehause.warehause1.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(NotFoundException e){
 //   String response = String.format("{\"status\": 404, \"message\": \"%s\"}", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
