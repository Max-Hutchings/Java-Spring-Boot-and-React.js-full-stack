package com.dfchallenge.twitterclone.exceptions;

public class InvalidUserException extends RuntimeException{

    public InvalidUserException(String message){
        super(message);
    }
}
