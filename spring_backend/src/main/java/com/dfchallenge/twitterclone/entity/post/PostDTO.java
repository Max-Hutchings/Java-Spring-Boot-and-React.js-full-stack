package com.dfchallenge.twitterclone.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class PostDTO {


    private int id;
    private String postContent;
    private int accountId;
    private String accountUsername;
    private Date createdDate;


    public PostDTO(int id, String postContent, int accountId, String accountUsername, Date createdDate) {
        this.postContent = postContent;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.accountUsername = accountUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
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
}
