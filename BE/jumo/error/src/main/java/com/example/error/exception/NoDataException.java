package com.example.error.exception;

import lombok.Getter;

@Getter
public class NoDataException extends RuntimeException{
    private final String message;

    public NoDataException(String message){
        super(message);
        this.message=message;
    }
}
