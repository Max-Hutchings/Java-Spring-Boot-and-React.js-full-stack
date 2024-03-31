package com.dfchallenge.twitterclone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(){
        super("Account already exists");

    }
}
