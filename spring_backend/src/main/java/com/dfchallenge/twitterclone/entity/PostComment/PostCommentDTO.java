package com.dfchallenge.twitterclone.entity.PostComment;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class PostCommentDTO {


    private Integer id;
    private Integer postId;
    private String commentContent;
    private Integer accountId;
    private String accountUsername;
    private Date createdDate;


    public PostCommentDTO(Integer id, Integer postId, String commentContent, Integer accountId, String accountUsername, Date createdDate){
        this.id = id;
        this.postId = postId;
        this.commentContent = commentContent;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.accountUsername = accountUsername;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "PostCommentDTO{" +
                "id=" + id +
                ", postId=" + postId +
                ", commentContent='" + commentContent + '\'' +
                ", accountId=" + accountId +
                ", accountUsername='" + accountUsername + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
