package com.warehause.warehause1.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
}
