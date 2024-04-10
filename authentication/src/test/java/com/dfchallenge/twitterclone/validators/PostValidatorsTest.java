package com.dfchallenge.twitterclone.validators;

import com.dfchallenge.twitterclone.dao.PostRepository;
import com.dfchallenge.twitterclone.entity.post.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class PostValidatorsTest {



    @Test
    @DisplayName("Should pass due to correct post length")
    public void testCheckCorrectLength(){

    assertDoesNotThrow(() -> PostValidators.checkLength("This is a valid post content"));
    }

    @Test
    @DisplayName("Should fail due to post content too long")
    public void testIncorrectLength_TooLong(){

        String tooLongString = "abc".repeat(100);

        assertThrows(IllegalArgumentException.class, () -> PostValidators.checkLength(tooLongString));
    }


    @Test
    @DisplayName("Should fail due to post content being empty")
    public void testIncorrectLength_Empty(){
        assertThrows(IllegalArgumentException.class, () -> PostValidators.checkLength(""));
    }
}
