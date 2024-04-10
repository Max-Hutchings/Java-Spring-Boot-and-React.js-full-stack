package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.PostCommentRepository;
import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import com.dfchallenge.twitterclone.exceptions.InvalidPostCommentInputException;
import com.dfchallenge.twitterclone.validators.PostCommentValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService{

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Override
    public PostComment savePostComment(PostComment postComment) {
        try {
            PostCommentValidators.checkCommentLength(postComment.getCommentContent());

            return postCommentRepository.save(postComment);
        }catch(IllegalArgumentException e){
            throw new InvalidPostCommentInputException(e.getMessage());
        }
    }

    @Override
    public List<PostComment> getPostComments(Integer postId) {

        return postCommentRepository.findAllByPostId(postId);

    }
}
