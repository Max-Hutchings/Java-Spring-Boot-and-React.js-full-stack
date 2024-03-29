package com.dfchallenge.twitterclone.middleware;

import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.AccountServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountService accountService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Account account = accountService.getAccountByEmail(email);
        if (account != null && PasswordHasher.checkPassword(password, account.getPassword())) {
            // If the account exists and the password matches, authenticate the user
            return new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
        }

        // If authentication fails, throw an exception or return null
        throw new BadCredentialsException("Invalid email or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
