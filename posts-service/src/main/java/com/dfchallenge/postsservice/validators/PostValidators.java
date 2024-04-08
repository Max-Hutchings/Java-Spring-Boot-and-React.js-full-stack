package com.dfchallenge.postsservice.validators;

import com.dfchallenge.postsservice.entity.post.Post;

public class PostValidators {

    public static void checkLength(Post post){
        String postContent = post.getPostContent();
        if(postContent.length() > 299){
            throw new IllegalArgumentException("Post content too long");
        }
    }


}
