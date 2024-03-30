package com.dfchallenge.twitterclone.security_helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @InjectMocks
    private JWTServices jwtServices;

    @BeforeEach
    public void setup() {
//        Setting the secret field within JWTService because the @value annotation doesn't work in tests without full
//        spring context.
        String testSecret = "57e51e380ea4ec5d902cb626ca02e732316a70caf6d33ec7507a07d4e7e77c44";
        ReflectionTestUtils.setField(jwtServices, "secret", testSecret);
    }

    @Test
    @DisplayName("Should generate token that is split into three parts by a '.'")
    public void whenGenerateToken_thenCorrect() {
        Integer accountId = 1;
        String token = jwtServices.generateToken(accountId);

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        assertEquals(3, token.split("\\.").length, "Token should have 3 parts separated by '.'");
    }


    @Test
    @DisplayName("Should extract the accountId from the token")
    public void whenExtractAccountIdFromToken_thenCorrect() {
        Integer expectedAccountId = 1;
        String token = jwtServices.generateToken(expectedAccountId);

        Integer accountId = jwtServices.extractAccountId(token);

        assertEquals(expectedAccountId, accountId, "Extracted account ID should match the expected value");
    }

    @Test
    @DisplayName("Should return true, token is valid")
    public void whenCheckTokenValidity_thenCorrect() {
        Integer accountId = 1;
        String token = jwtServices.generateToken(accountId);

        boolean isValid = jwtServices.isTokenValid(token, accountId);

        assertTrue(isValid, "Token should be valid");
    }


}
