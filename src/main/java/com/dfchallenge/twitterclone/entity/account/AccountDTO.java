package com.dfchallenge.twitterclone.entity.account;

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

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", role=" + role +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
