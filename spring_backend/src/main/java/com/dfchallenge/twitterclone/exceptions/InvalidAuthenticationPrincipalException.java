package com.dfchallenge.twitterclone.exceptions;

public class InvalidAuthenticationPrincipalException extends RuntimeException{

    public InvalidAuthenticationPrincipalException(){
        super("Invalid authentication principal");
    }
}
