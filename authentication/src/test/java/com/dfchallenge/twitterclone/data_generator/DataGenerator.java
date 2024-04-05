package com.dfchallenge.twitterclone.data_generator;

import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.service.AccountService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class DataGenerator {

    private final AccountService accountService;

    @Value("${jwt.secret}")
    private String secret;

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


    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateOutOfDateToken(Integer accountId) {
        final int expirationTime = -1000;
        return Jwts
                .builder()
                .setSubject(Integer.toString(accountId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
