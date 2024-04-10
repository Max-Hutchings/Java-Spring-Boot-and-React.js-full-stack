package com.dfchallenge.twitterclone.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PostCommentValidatorsTest {



    @Test
    @DisplayName("Should pass validation checks")
    public void successfulValidation(){
        String postContent = "This is my first comment";
        assertDoesNotThrow( () -> PostCommentValidators.checkCommentLength(postContent));
    }

    @Test
    @DisplayName("Should fail due to too short comment")
    public void failValidation_tooShort(){
        String postContent = "";
        assertThrows(IllegalArgumentException.class, () -> PostCommentValidators.checkCommentLength(postContent));
    }


    @Test
    @DisplayName("Should fail due to too long comment")
    public void failValidation_tooLong(){
        String postContent = "a".repeat(201);
        assertThrows(IllegalArgumentException.class, () -> PostCommentValidators.checkCommentLength(postContent));

    }
}
