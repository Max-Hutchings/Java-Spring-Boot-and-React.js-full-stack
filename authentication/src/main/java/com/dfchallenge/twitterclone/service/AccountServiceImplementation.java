package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.exceptions.AccountAlreadyExistsException;
import com.dfchallenge.twitterclone.exceptions.InvalidAccountInputException;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.validators.AccountValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccountServiceImplementation implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {

        try {
//        Add check and logic before saving
            AccountValidators.checkEmail(account.getEmail());
            AccountValidators.checkUsername(account.getUsername());
            AccountValidators.checkFName(account.getFName());
            AccountValidators.checkLName(account.getLName());
            AccountValidators.checkPassword(account.getPassword());
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountInputException(e.getMessage());
        }

//        Hashing the password
        String plainPassword = account.getPassword();
        String hashedPassword = PasswordHasher.hashPassword(plainPassword);
        account.setPassword(hashedPassword);

        System.out.println(account);

        try {
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException error) {
            throw new AccountAlreadyExistsException();

        }
    }


    public Optional<Account> getAccountById(Integer id) {
        return accountRepository.findById(id);
    }


    public Optional<Account> getAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }



    private Collection<? extends GrantedAuthority> getAuthorities(Account account) {
        return account.getAuthorities();
    }
}
