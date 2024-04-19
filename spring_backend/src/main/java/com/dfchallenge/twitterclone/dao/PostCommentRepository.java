package com.dfchallenge.twitterclone.dao;

import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import com.dfchallenge.twitterclone.entity.PostComment.PostCommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
    List<PostComment> findAllByPostId(Integer postId);


    @Query("SELECT new com.dfchallenge.twitterclone.entity.PostComment.PostCommentDTO(pc.id, pc.postId, pc.commentContent, a.id, a.username, pc.createdDate) " +
            "FROM PostComment pc JOIN Account a ON pc.accountId = a.id WHERE pc.postId = :postId")
    List<PostCommentDTO> findAllPostCommentsWithAccountDetails(Integer postId);
}
