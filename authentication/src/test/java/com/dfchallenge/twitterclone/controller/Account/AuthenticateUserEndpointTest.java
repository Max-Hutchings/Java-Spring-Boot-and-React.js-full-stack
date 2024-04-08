package com.dfchallenge.twitterclone.controller.Account;

import com.dfchallenge.twitterclone.controller.AccountController;
import com.dfchallenge.twitterclone.exceptions.InvalidUserException;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.service.AccountService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticateUserEndpointTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private JWTServices jwtServices;

    @MockBean
    private CookieAdder cookieAdder;


    private final String ENDPOINT_URL = "/authentication/authenticate-user";

    @Test
    @DisplayName("Successful Validation - Returns 200 OK")
    public void whenAccountIdMatchesTokenId_thenValidationSuccess() throws Exception {
        String jsonRequest = """
        {
            "token": "valid_jwt_token",
            "accountId": "1"
        }
    """;

        when(jwtServices.extractAccountId("valid_jwt_token")).thenReturn(1);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Successful Validation - Returns accountValidated boolean")
    public void whenAccountIdMatchesTokenId_returnBoolean() throws Exception {
        String jsonRequest = """
        {
            "token": "valid_jwt_token",
            "accountId": "1"
        }
    """;

        when(jwtServices.extractAccountId("valid_jwt_token")).thenReturn(1);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(jsonPath("$.accountValidated").value(true));
    }

    @Test
    @DisplayName("Successful Validation - Returns accountId")
    public void whenAccountIdMatchesTokenId_returnAccountId() throws Exception {
        String jsonRequest = """
        {
            "token": "valid_jwt_token",
            "accountId": "1"
        }
    """;

        when(jwtServices.extractAccountId("valid_jwt_token")).thenReturn(1);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))

                .andExpect(jsonPath("$.accountId").value(1));
    }

    @Test
    @DisplayName("Failed Validation - Account Id does not match Token Id")
    public void whenAccountIdDoesNotMatchTokenId_thenUnauthorizedResponse() throws Exception {
        String jsonRequest = """
        {
            "token": "valid_jwt_token",
            "accountId": "2"
        }
    """;

        when(jwtServices.extractAccountId("valid_jwt_token")).thenReturn(1);
        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.accountValidated").value(false))
                .andExpect(jsonPath("$.errors").value("Account id does not match token id"));
    }

    @Test
    @DisplayName("Failed Validation - Invalid Token")
    public void whenInvalidToken_thenUnauthorizedResponse() throws Exception {
        String jsonRequest = """
        {
            "token": "invalid_jwt_token",
            "accountId": "1"
        }
    """;

        when(jwtServices.extractAccountId("invalid_jwt_token")).thenThrow(new InvalidUserException("Invalid token"));

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.accountValidated").value(false))
                .andExpect(jsonPath("$.errors").value("Invalid token"));
    }


}
