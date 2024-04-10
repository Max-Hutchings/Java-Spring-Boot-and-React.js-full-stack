package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.PostComment.PostComment;

import java.util.List;

public interface PostCommentService {

    PostComment savePostComment(PostComment postComment);
    List<PostComment> getPostComments(Integer postId);
}
