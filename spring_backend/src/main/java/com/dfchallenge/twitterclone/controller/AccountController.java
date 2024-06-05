package com.dfchallenge.twitterclone.controller;


import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.entity.account.AccountDTO;
import com.dfchallenge.twitterclone.exceptions.*;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    private final AccountService accountService;
    private final JWTServices jwtServices;
    private final CookieAdder cookieAdder;
    private final AuthenticationService authenticationService;


    @Autowired
    public AccountController(AccountService accountService, JWTServices jwtServices, CookieAdder cookieAdder,
                             AuthenticationService authenticationService) {
        this.accountService = accountService;
        this.jwtServices = jwtServices;
        this.cookieAdder = cookieAdder;
        this.authenticationService = authenticationService;

    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String message, Exception e) {
        logger.error(message, e);
        Map<String, String> bodyMessage = Map.of("message", message, "errors", e.getMessage());
        return ResponseEntity.status(status).body(bodyMessage);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody Account account, HttpServletResponse response) {
        logger.info("Called signup");
        try{
            logger.debug("Account details: {}", account.toString());

            Account newAccount = accountService.saveAccount(account);
            String jwtToken = jwtServices.generateToken(newAccount.getId());
            cookieAdder.addTokenToCookie(jwtToken, response);

            AccountDTO accountDTO = new AccountDTO(newAccount);

            return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);

        }
        catch (InvalidAccountInputException e) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Failed to save account due to input", e);
        } catch (AccountAlreadyExistsException e) {
            return buildErrorResponse(HttpStatus.CONFLICT, "Account Already Exists", e);
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save account", e);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body, HttpServletResponse response) {
        logger.info("Called login");

        try {
            Account account = accountService.getAccountByEmail(body.get("email"));
            PasswordHasher.checkPassword(body.get("password"), account.getPassword());

            String jwtToken = jwtServices.generateToken(account.getId());
            cookieAdder.addTokenToCookie(jwtToken, response);

            AccountDTO accountDTO = new AccountDTO(account);

            return ResponseEntity.status(HttpStatus.OK).body(accountDTO);

        } catch (FailedToGetAccountException failedToGetAccountException) {
            System.out.println(failedToGetAccountException.getMessage());
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Failed to find account", failedToGetAccountException);
        } catch (PasswordDoesntMatchException passwordDoesntMatchException) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Incorrect password", passwordDoesntMatchException);
        }
    }





    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        System.out.println("Print logout");
        Cookie cookie = new Cookie("token", "");

        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Map<String, String> responseBody = Map.of("message", "Logged out successfully");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/validate-jwt")
    public ResponseEntity<?> validateJWT(HttpServletResponse response) {
        logger.info("Called Validate jwt");

        try{
            Account account = authenticationService.getAuthenticatedAccount();

            String newJwt = jwtServices.generateToken(account.getId());
            cookieAdder.addTokenToCookie(newJwt, response);

            AccountDTO accountDTO = new AccountDTO(account);

            return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
        } catch (AuthenticationException | InvalidAuthenticationPrincipalException e) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }


    @PostMapping("/authenticate-user")
    public ResponseEntity<?> validateUser(@RequestBody Map<String, String> body) {
        logger.info("Called authenticate user");
        try {
            String token = body.get("token");
            int accountId = Integer.parseInt(body.get("accountId"));

            Integer accountIdFromToken = jwtServices.extractAccountId(token);
            authenticationService.matchIds(accountId, accountIdFromToken);


            Map<String, ?> responseMessage = Map.of("accountValidated", true, "accountId", accountIdFromToken);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);


        }catch(InvalidUserException invalidUserException){
            Map<String, ?> responseMessage = Map.of("accountValidated", false, "errors", invalidUserException.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }

    }

}
