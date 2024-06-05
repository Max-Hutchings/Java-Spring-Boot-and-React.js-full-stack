package com.dfchallenge.twitterclone.security_helpers;

import com.dfchallenge.twitterclone.exceptions.PasswordDoesntMatchException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    private static final int salts = 10;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(salts);

    public static String hashPassword(String plainPassword){
        return encoder.encode(plainPassword);
    }


    public static void checkPassword(String plainPassword, String hashedPassword){
        if (encoder.matches(plainPassword, hashedPassword)) return;
        throw new PasswordDoesntMatchException();
    }
}
