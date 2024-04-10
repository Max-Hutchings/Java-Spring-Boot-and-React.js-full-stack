package com.dfchallenge.twitterclone.dao;

import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
    List<PostComment> findAllByPostId(Integer postId);
}
