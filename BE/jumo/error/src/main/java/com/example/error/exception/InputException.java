package com.example.error.exception;

public class InputException extends RuntimeException{
    private final String message;

    public InputException(String message){
        super(message);
        this.message= message;
    }
}
