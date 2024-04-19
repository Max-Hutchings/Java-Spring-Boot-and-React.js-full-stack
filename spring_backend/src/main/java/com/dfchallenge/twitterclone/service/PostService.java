package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.entity.post.PostDTO;

import java.util.List;


public interface PostService {

       Post savePost(Post post);

       List<PostDTO> getAllPosts();

        Post getPost();




}
