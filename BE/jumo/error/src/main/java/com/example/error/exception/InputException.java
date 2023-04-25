package com.example.error.exception;

public class InputException extends RuntimeException{
    private String message;

    public InputException(String message){
        super(message);
        this.message= message;
    }
}
