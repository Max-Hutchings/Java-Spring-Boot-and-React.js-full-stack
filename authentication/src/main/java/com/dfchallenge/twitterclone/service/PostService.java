package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.post.Post;

import java.util.List;


public interface PostService {

       Post savePost(Post post);

       List<Post> getAllPosts();

        Post getPost();




}
