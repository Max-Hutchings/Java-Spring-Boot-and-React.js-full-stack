package com.dfchallenge.twitterclone.validators;

import org.springframework.security.core.parameters.P;

public class PostCommentValidators {

    public static void checkCommentLength(String comment) {
        if (comment.length() > 200) {
            throw new IllegalArgumentException("Comment length must be less than 140 characters");
        }

        if(comment.isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
    }
}
