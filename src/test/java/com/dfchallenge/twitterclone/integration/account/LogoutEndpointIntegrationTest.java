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
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class LogoutEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup(){
        try{
            accountRepository.deleteAll();
        }catch(Exception e){
            System.out.println("Failed to prepare for logout tests");
        }
    }


    @AfterEach
    public void cleanup(){
        try{
            accountRepository.deleteAll();
        }catch(Exception e){
            System.out.println("Failed to clean up after logout tests");
        }
    }



    @Test
    @DisplayName("Logout Endpoint - Should Clear Token Cookie")
    public void logoutUserTest() throws Exception {
        Account account = dataGenerator.addAccountToDatabase();
        Integer accountId = account.getId();
        String jwt = jwtServices.generateToken(accountId);

        MockCookie mockTokenCookie = new MockCookie("token", jwt);

        mockMvc.perform(post("/authentication/logout")
                        .cookie(mockTokenCookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"))
                .andExpect(cookie().value("token", ""))
                .andExpect(cookie().maxAge("token", 0));
    }
}
