package com.dfchallenge.twitterclone.integration.account;


import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security_helpers.PasswordHasher;
import com.dfchallenge.twitterclone.service.AccountService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    private final String ENDPOINT_URL = "/authentication/login";

    @Before
    public void setup() {
        try {
            String hashedPassword = PasswordHasher.hashPassword("PassWord123##!");
            Account account = new Account("not_quite_007", "Jason", "Bourne", "jason@gmail.com", hashedPassword, "USER");
            accountService.saveAccount(account);
        } catch (Exception e) {
            throw new RuntimeException("Failed to carry out pre-login test setup: " + e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Successful login - Should return status 200 and account details")
    public void successfulLoginTest() throws Exception{
        String jsonRequest = """
                {
                    "email": "jason@gmail.com",
                    "password": "PassWord123##!"
                }
                """;
        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));


        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        System.out.println("Response: " + response.getContentAsString());

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("best_username_ever"))
                .andExpect(jsonPath("$.email").value("jason@gmail.com"))
                .andExpect(jsonPath("$.id", Matchers.anything()))
                .andExpect(jsonPath("$.fName").value("Jason"))
                .andExpect(jsonPath("$.lName").value("Borne"))
                .andExpect(jsonPath("$.authorities[0].authority").value("USER"))
                .andExpect(jsonPath("$.password").doesNotExist());




    }
}
