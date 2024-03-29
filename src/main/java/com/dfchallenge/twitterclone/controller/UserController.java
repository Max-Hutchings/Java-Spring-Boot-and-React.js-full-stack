package com.dfchallenge.twitterclone.controller;


import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.AccountServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UserController {

    private final AccountService accountService;

    @Autowired
    public UserController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<Account> createUser(@RequestBody Account account) {
        Account newAccount = accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

}
