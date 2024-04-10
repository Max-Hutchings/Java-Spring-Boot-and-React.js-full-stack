package com.dfchallenge.twitterclone.entity.post;

import jakarta.persistence.*;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="post_content", nullable = false, length = 300)
    private String postContent;

    @Column(name="account_id", nullable = false)
    private int accountId;


    public Post(String postContent, int accountId){
        this.postContent = postContent;
        this.accountId = accountId;
    }

    public Post(){}

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
}
