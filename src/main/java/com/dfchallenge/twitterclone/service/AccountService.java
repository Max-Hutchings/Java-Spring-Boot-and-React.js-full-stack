package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface AccountService {

    Account saveAccount(Account account);
    Optional<Account> getAccountById(Integer id);

    Optional<Account> getAccountByEmail(String email);
}
