package com.dfchallenge.twitterclone.controller.Account;

import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class LoginEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTServices jwtServices;

    @MockBean
    private CookieAdder cookieAdder;

    @MockBean
    private AccountService accountService;

    private final String ENDPOINT_URL = "/authentication/login";

    private final String hashedPassword = PasswordHasher.hashPassword("PassWord233##!");

    private final Account mockAccount = new Account("best_username_ever", "Jason", "Borne", "jason@gmail.com", hashedPassword, "USER");

    @Test
    @DisplayName("Successful Login - Returns 200 OK")
    public void whenValidCredentials_thenLoginSuccess() throws Exception {
        String jsonRequest = """
            {
                "email": "jason@gmail.com",
                "password": "PassWord233##!"
            }
        """;

        when(accountService.getAccountByEmail("jason@gmail.com")).thenReturn(Optional.of(mockAccount));
        when(jwtServices.generateToken(mockAccount.getId())).thenReturn("jwt_token");

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Failed Login - Account Not Found")
    public void whenAccountNotFound_thenNotFoundResponse() throws Exception {
        String jsonRequest = """
            {
                "email": "unknown@gmail.com",
                "password": "PassWord233##!"
            }
        """;

        when(accountService.getAccountByEmail("unknown@gmail.com")).thenReturn(Optional.empty());

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Failed to find account"));
    }

    @Test
    @DisplayName("Failed Login - Incorrect Password")
    public void whenIncorrectPassword_thenUnauthorizedResponse() throws Exception {
        String jsonRequest = """
            {
                "email": "jason@gmail.com",
                "password": "WrongPassword"
            }
        """;

        when(accountService.getAccountByEmail("jason@gmail.com")).thenReturn(Optional.of(mockAccount));

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Incorrect password"));
    }

    // Additional tests can be added here
}
