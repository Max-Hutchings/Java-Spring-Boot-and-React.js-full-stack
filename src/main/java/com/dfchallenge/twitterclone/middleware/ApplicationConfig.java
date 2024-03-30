package com.dfchallenge.twitterclone.middleware;

import com.dfchallenge.twitterclone.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class ApplicationConfig {

    private AccountService accountService;

    @Bean
    public UserDetailsService userDetailsService(){
        return userId -> accountService.getAccountById(Integer.parseInt(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
