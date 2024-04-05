package com.dfchallenge.twitterclone.integration.account;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.data_generator.DataGenerator;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticateUserEndpointIntegrationTest {

    private MockMvc mockMvc;
    private AccountRepository accountRepository;
    private DataGenerator dataGenerator;
    private JWTServices jwtServices;

    @Autowired
    public AuthenticateUserEndpointIntegrationTest(MockMvc mockMvc, AccountRepository accountRepository, DataGenerator dataGenerator, JWTServices jwtServices) {
        this.mockMvc = mockMvc;
        this.accountRepository = accountRepository;
        this.dataGenerator = dataGenerator;
        this.jwtServices = jwtServices;
    }

    private final String ENDPOINT_URL = "/authentication/authenticate-user";

    private Account account;
    private String validJWT;

    @BeforeEach
    public void setup() {
        try {
            accountRepository.deleteAll();
            account = dataGenerator.addAccountToDatabase();
            validJWT = jwtServices.generateToken(account.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to carry out pre-login test setup: " + e.getMessage(), e);
        }
    }

    @AfterEach
    public void cleanup(){
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return OK")
    public void successfulValidation_CheckOk() throws Exception {

        String jsonRequest = String.format("""
    {
        "token": "%s",
        "accountId": "%d"
    }
    """, validJWT, account.getId());

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountValidated").value(true))
                .andExpect(jsonPath("$.accountId").value(account.getId()));

        ;

    }

}
