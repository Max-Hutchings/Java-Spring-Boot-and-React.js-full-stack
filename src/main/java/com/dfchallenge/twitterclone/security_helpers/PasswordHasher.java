package com.dfchallenge.twitterclone.security_helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    private static final int salts = 10;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(salts);

    public static String hashPassword(String plainPassword){
        return encoder.encode(plainPassword);
    }


    public static boolean checkPassword(String plainPassword, String hashedPassword){
        return encoder.matches(plainPassword, hashedPassword);
    }
}
