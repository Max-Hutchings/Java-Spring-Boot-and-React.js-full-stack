package com.dfchallenge.twitterclone.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UserController {


    @PostMapping("/create-account")
    public void createUser(){

    }

}
