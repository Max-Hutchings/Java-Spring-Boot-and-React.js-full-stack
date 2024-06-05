package com.dfchallenge.twitterclone.exceptions;

public class PasswordDoesntMatchException extends RuntimeException{

    public PasswordDoesntMatchException(){
        super("Incorrect password provided");
    }
}
