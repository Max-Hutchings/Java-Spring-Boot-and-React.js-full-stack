package com.dfchallenge.twitterclone.exceptions;

public class NoJWTException extends RuntimeException{

    public NoJWTException(){
        super("Token is either null or does not exist");
    }
}
