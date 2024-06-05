package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.exceptions.AuthenticationException;
import com.dfchallenge.twitterclone.exceptions.InvalidAuthenticationPrincipalException;
import com.dfchallenge.twitterclone.exceptions.InvalidUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Account getAuthenticatedAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException();
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Account)) {
            throw new InvalidAuthenticationPrincipalException();
        }

        return (Account) principal;
    }

    public void matchIds(int accountId, Integer accountIdFromToken) {
        if (!Integer.valueOf(accountId).equals(accountIdFromToken)) {
            throw new InvalidUserException("Account id does not match token id");
        }
    }

}

