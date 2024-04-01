package com.dfchallenge.twitterclone.data_generator;

import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DataGenerator {

    private final AccountService accountService;

    @Autowired
    public DataGenerator(AccountService accountService){
        this.accountService = accountService;
    }

    public Account addAccountToDatabase(){
        Account account;
        account = new Account("not_quite_007", "Jason", "Bourne", "jason@gmail.com",
                "PassWord233##!", "USER");
        account = accountService.saveAccount(account);
        System.out.println(account.toString());
        return account;
    }
}
