package com.example.user_register.exception.user;

public class InvalidBirthDateFormatException extends RuntimeException{
    public InvalidBirthDateFormatException(String message){
        super(message);
    }

    public InvalidBirthDateFormatException(String message, Throwable cause){
        super(message, cause);
    }
}
