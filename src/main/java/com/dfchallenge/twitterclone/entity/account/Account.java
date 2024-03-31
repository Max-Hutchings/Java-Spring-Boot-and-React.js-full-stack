package com.dfchallenge.twitterclone.entity.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//implements UserDetails
@Entity
@Table(name="account")
public class Account implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username", unique = true, length=70, nullable = false)
    private String username;

    @JsonProperty("fName")
    @Column(name="first_name", length=50, nullable = false)
    private String fName;

    @JsonProperty("lName")
    @Column(name="last_name", length=50, nullable = false)
    private String lName;

    @Column(name="email", unique = true, length = 50, nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    public Account() {}

    public Account(String username, String fName, String lName, String email, String password, String role){
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        setRoleFromString(role);
    }

    public void setRoleFromString(String roleStr) {
        if (roleStr != null && !roleStr.isEmpty()) {
            try {
                this.role = Role.valueOf(roleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Cannot match the enum");
                throw new IllegalArgumentException("Invalid role: " + roleStr);
            }
        } else {
            this.role = Role.USER;
        }
    }


    public String getUsername() {
        return username;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


    public Role getRole() {
        return this.role;
    }
}
