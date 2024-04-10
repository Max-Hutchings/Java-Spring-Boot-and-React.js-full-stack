package com.dfchallenge.twitterclone.exceptions;

public class InvalidPostCommentInputException extends RuntimeException{
    public InvalidPostCommentInputException(String message) {
        super(message);
    }
}
