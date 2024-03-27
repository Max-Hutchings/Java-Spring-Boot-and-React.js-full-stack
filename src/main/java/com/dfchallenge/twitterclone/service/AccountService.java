package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.entity.Account;

public interface AccountService {

    Account saveAccount(Account account);
    Account getAccountById(int id);

    Account getAccountByEmail(String email);
}
