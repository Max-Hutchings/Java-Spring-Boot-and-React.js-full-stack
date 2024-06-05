package com.dfchallenge.twitterclone.exceptions;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(){
        super("User is not authenticated");
    }
}
