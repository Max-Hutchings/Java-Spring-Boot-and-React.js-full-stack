package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security.PasswordHasher;
import com.dfchallenge.twitterclone.validators.AccountValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImplementation implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account){

//        Add check and logic before saving
        AccountValidators.checkEmail(account.getEmail());
        AccountValidators.checkUsername(account.getUsername());
        AccountValidators.checkFName(account.getFName());
        AccountValidators.checkLName(account.getLName());
        AccountValidators.checkPassword(account.getPassword());

//        Hashing the password
        String plainPassword = account.getPassword();
        String hashedPassword = PasswordHasher.hashPassword(plainPassword);
        account.setPassword(hashedPassword);


        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(int id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    @Override
    public Account getAccountByEmail(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.orElse(null);
    }

}
