package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import com.dfchallenge.twitterclone.entity.PostComment.PostCommentDTO;

import java.util.List;

public interface PostCommentService {

    PostComment savePostComment(PostComment postComment);
    List<PostCommentDTO> getPostComments(Integer postId);
}
