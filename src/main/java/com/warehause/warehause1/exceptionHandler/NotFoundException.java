package com.warehause.warehause1.exceptionHandler;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
