package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.validators.AccountValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Account> accountOpt = accountRepository.findByEmail(email);
//        Account account = accountOpt.orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
//
//        // You need to convert your Account entity to a Spring Security UserDetails object
//        return new org.springframework.security.core.userdetails.User(
//                account.getEmail(),
//                account.getPassword(),
//                account.isEnabled(),
//                true, true, true,
//                getAuthorities(account)
//        );
//    }

    private Collection<? extends GrantedAuthority> getAuthorities(Account account) {
        return account.getAuthorities();
    }
}
