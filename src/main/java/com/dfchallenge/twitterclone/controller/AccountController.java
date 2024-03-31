package com.dfchallenge.twitterclone.controller;


import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.exceptions.AccountAlreadyExistsException;
import com.dfchallenge.twitterclone.exceptions.InvalidAccountInputException;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AccountController {

    private final AccountService accountService;
    private final JWTServices jwtServices;
    private final CookieAdder cookieAdder;


    @Autowired
    public AccountController(AccountService accountService, JWTServices jwtServices, CookieAdder cookieAdder){
        this.accountService = accountService;
        this.jwtServices = jwtServices;
        this.cookieAdder = cookieAdder;

    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createUser(@RequestBody Account account, HttpServletResponse response) {
        try {
            System.out.println(account.toString());
            Account newAccount;
            try {
                newAccount = accountService.saveAccount(account);
            } catch (InvalidAccountInputException e) {
                Map<String, String> bodyMessage = Map.of("message", "Failed to save account due to input", "errors", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyMessage);
            } catch(AccountAlreadyExistsException e){
                Map<String, String> bodyMessage = Map.of("message", "Account Already Exists", "errors", e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(bodyMessage);
            }


            String jwtToken = jwtServices.generateToken(newAccount.getId());
            cookieAdder.addTokenToCookie(jwtToken, response);

            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        }catch(Exception e){
            Map<String, String> bodyMessage = Map.of("message", "Failed to save account", "errors", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyMessage);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body, HttpServletResponse response){
        String email = body.get("email");
        String password = body.get("password");

        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!account.isPresent()){
            Map<String, String> responseBody = Map.of("message", "Failed to find account");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        boolean passwordsMatch = PasswordHasher.checkPassword(password, account.get().getPassword());

        if (!passwordsMatch){
            Map<String, String> responseBody = Map.of("message", "Incorrect password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }

        String jwtToken = jwtServices.generateToken(account.get().getId());

        cookieAdder.addTokenToCookie(jwtToken, response);

        return ResponseEntity.status(HttpStatus.OK).body(account);




    }



}
