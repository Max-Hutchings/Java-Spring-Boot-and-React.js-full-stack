package com.dfchallenge.twitterclone.controller;


import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.entity.account.AccountDTO;
import com.dfchallenge.twitterclone.exceptions.AccountAlreadyExistsException;
import com.dfchallenge.twitterclone.exceptions.InvalidAccountInputException;
import com.dfchallenge.twitterclone.exceptions.InvalidUserException;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AccountController {

    private final AccountService accountService;
    private final JWTServices jwtServices;
    private final CookieAdder cookieAdder;


    @Autowired
    public AccountController(AccountService accountService, JWTServices jwtServices, CookieAdder cookieAdder) {
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
            } catch (AccountAlreadyExistsException e) {
                Map<String, String> bodyMessage = Map.of("message", "Account Already Exists", "errors", e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(bodyMessage);
            }

            String jwtToken = jwtServices.generateToken(newAccount.getId());
            cookieAdder.addTokenToCookie(jwtToken, response);

            AccountDTO accountDTO = new AccountDTO(newAccount);
            System.out.println(accountDTO.toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
        } catch (Exception e) {
            Map<String, String> bodyMessage = Map.of("message", "Failed to save account", "errors", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyMessage);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<Account> optionalAccount = accountService.getAccountByEmail(email);

        if (!optionalAccount.isPresent()) {
            Map<String, String> responseBody = Map.of("message", "Failed to find account");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        Account account = optionalAccount.get();

        boolean passwordsMatch = PasswordHasher.checkPassword(password, account.getPassword());
        if (!passwordsMatch) {
            Map<String, String> responseBody = Map.of("message", "Incorrect password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }

        String jwtToken = jwtServices.generateToken(account.getId());
        cookieAdder.addTokenToCookie(jwtToken, response);

        AccountDTO accountDTO = new AccountDTO(account);

        return ResponseEntity.status(HttpStatus.OK).body(accountDTO);


    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");

        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Map<String, String> responseBody = Map.of("message", "Logged out successfully");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/validate-jwt")
    public ResponseEntity<?> validateJWT(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("+==++++++++");
            Object principal = authentication.getPrincipal();

            if (principal instanceof Account) {
                Account account = (Account) principal;

                String newJwt = jwtServices.generateToken(account.getId());
                cookieAdder.addTokenToCookie(newJwt, response);

                AccountDTO accountDTO = new AccountDTO(account);


                return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while fetching account details.");
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
    }

    @PostMapping("/authenticate-user")
    public ResponseEntity<?> validateUser(@RequestBody Map<String, String> body, HttpServletResponse response){
        try {
            System.out.println("=============== BODY =======================");
            System.out.println(body);
            String token = body.get("token");
            int accountId = Integer.parseInt(body.get("accountId"));
            System.out.println("======================================");
            System.out.println(token);
            System.out.println(accountId);

            Integer accountIdFromToken = jwtServices.extractAccountId(token);
            System.out.println(accountIdFromToken);
            if ( accountIdFromToken == accountId){
                Map<String, ?> responseMessage = Map.of("accountValidated", true, "accountId", accountIdFromToken);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }

            throw new InvalidUserException("Account id does not match token id");
        }catch(Exception e){
            Map<String, ?> responseMessage = Map.of("accountValidated", false, "errors", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }

    }
}
