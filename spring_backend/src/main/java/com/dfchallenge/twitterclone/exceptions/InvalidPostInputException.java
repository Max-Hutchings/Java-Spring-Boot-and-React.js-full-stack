package com.dfchallenge.twitterclone.exceptions;

public class InvalidPostInputException extends RuntimeException{
    public InvalidPostInputException(String message){
        super(message);
    }
}
