package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.account.Account;

import java.util.Optional;


public interface AccountService {

    Account saveAccount(Account account);
    Optional<Account> getAccountById(Integer id);

    Account getAccountByEmail(String email);
}
