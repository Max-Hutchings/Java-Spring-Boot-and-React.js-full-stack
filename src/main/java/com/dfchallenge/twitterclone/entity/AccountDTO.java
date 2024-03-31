package com.dfchallenge.twitterclone.entity;

public class AccountDTO {
    private Integer id;
    private String email;
    private String username;
    private String fName;
    private String lName;
    private Role role;


    public AccountDTO(Account account){
        this.id = account.getId();
        this.email = account.getEmail();
        this.username = account.getUsername();
        this.fName = account.getFName();
        this.lName = account.getLName();
        this.role = account.getRole();
    }

}
