package com.dfchallenge.twitterclone.entity.PostComment;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="post_comment")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="post_id", nullable = false)
    private Integer postId;

    @Column(name="comment_content", nullable = false, length = 200)
    private String commentContent;

    @Column(name="account_id", nullable = false)
    private Integer accountId;

    @Column(name="created_date", nullable = false)
    private Date createdDate;


    public PostComment(Integer postId, String commentContent, Integer accountId){
        this.postId = postId;
        this.commentContent = commentContent;
        this.accountId = accountId;
        this.createdDate = new Date();
    }

    public PostComment(){}

    public Date getCreatedDate(){
        return createdDate;
    }

    public void setCreatedDate(Date createdDate){
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
