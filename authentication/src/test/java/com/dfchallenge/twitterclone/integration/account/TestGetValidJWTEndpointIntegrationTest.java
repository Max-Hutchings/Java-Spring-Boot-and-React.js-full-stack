package com.dfchallenge.twitterclone.integration.account;

import com.dfchallenge.twitterclone.controller.AccountController;
import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestGetValidJWTEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    Account account;

    @BeforeEach
    public void setup(){
        try {
            accountRepository.deleteAll();
            account = new Account("best_username_ever", "Jason", "Borne", "jason@gmail.com", "PassWord233##!",
                    "USER");
            account = accountService.saveAccount(account);
            System.out.println("Successfully prepared for test");
        }catch(Exception e){
            System.out.println("Failed test preparation");
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void cleanup(){
        accountRepository.deleteAll();
    }

    @Test
    public void getTestJWTAndIdTest() throws Exception {
        Map<String, String> requestBody = Map.of("accountId", Integer.toString(account.getId()));
        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post("/authentication/test/get-valid-jwt") // Use post instead of get
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", Matchers.anything()))
                .andExpect(jsonPath("$.token", Matchers.anything()));
    }

}