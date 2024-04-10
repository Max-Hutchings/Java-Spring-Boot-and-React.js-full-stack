package com.dfchallenge.twitterclone.dao;

import com.dfchallenge.twitterclone.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
