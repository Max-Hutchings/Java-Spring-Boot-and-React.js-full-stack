package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.PostRepository;
import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.exceptions.InvalidPostInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dfchallenge.twitterclone.validators.PostValidators.checkLength;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        try {
            checkLength(post.getPostContent());

            return postRepository.save(post);
        }catch (IllegalArgumentException e){
            throw new InvalidPostInputException(e.getMessage());
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    @Override
    public Post getPost() {
        return null;
    }
}
