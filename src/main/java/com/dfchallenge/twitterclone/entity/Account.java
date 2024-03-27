package com.dfchallenge.twitterclone.entity;


import jakarta.persistence.*;

@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="first_name")
    private String fName;

    @Column(name="last_name")
    private String lName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    public String getUsername() {
        return username;
    }

    public Account(String username, String fName, String lName, String email, String password){
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
    }

    public int getId(){
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
