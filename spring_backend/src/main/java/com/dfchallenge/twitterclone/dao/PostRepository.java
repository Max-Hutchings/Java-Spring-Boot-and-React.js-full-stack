package com.dfchallenge.twitterclone.dao;

import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.entity.post.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT new com.dfchallenge.twitterclone.entity.post.PostDTO(p.id, p.postContent, a.id, a.username, p.createdDate)" +
            "FROM Post p INNER JOIN Account a ON p.accountId = a.id")
    List<PostDTO> findALLPostsWithAccountDetails();

}
