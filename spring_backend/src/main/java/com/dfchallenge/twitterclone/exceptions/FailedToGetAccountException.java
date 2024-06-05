package com.dfchallenge.twitterclone.exceptions;

public class FailedToGetAccountException extends RuntimeException{

    public FailedToGetAccountException(){
        super("Failed to get account");
    }
}
