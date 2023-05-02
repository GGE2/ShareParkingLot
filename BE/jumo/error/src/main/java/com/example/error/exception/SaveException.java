package com.example.error.exception;

import lombok.Getter;

@Getter
public class SaveException extends RuntimeException{
    private String message;

    public SaveException(String message){
        super(message);
        this.message=message;
    }
}
