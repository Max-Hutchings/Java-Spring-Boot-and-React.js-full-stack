package com.dfchallenge.twitterclone.exceptions;

public class InvalidAccountInputException extends RuntimeException{

    public InvalidAccountInputException(String message){
        super(message);
    }
}
