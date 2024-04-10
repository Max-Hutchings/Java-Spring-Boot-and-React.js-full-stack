package com.dfchallenge.twitterclone.validators;

import com.dfchallenge.twitterclone.entity.post.Post;

public class PostValidators {

    public static void checkLength(String postContent){
        if(postContent.length() > 299){
            throw new IllegalArgumentException("Post content too long");
        }

        if(postContent.isEmpty()){
            throw new IllegalArgumentException("Post content too short");
        }
    }


}
