package com.dfchallenge.twitterclone.integration.account;



import com.dfchallenge.twitterclone.controller.AccountController;
import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.data_generator.DataGenerator;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidateJWTEndpointTest {

    private MockMvc mockMvc;
    private AccountRepository accountRepository;
    private DataGenerator dataGenerator;
    private JWTServices jwtServices;


    @Autowired
    public ValidateJWTEndpointTest(MockMvc mockMvc, AccountRepository accountRepository, DataGenerator dataGenerator, JWTServices jwtServices){
        this.mockMvc = mockMvc;
        this.accountRepository = accountRepository;
        this.dataGenerator = dataGenerator;
        this.jwtServices = jwtServices;
    }

    private final String ENDPOINT_URL = "/authentication/validate-jwt";

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
    @DisplayName("Should validate request and return user details")
    public void successfulValidationTest() throws Exception{
        ResultActions resultActions = mockMvc.perform(get(ENDPOINT_URL)
                .cookie(new MockCookie("token", validJWT))
                .with(csrf()));

        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        System.out.println("Response: " + response.getContentAsString());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("not_quite_007"))
                .andExpect(jsonPath("$.email").value("jason@gmail.com"))
                .andExpect(jsonPath("$.id", Matchers.anything()))
                .andExpect(jsonPath("$.fName").value("Jason"))
                .andExpect(jsonPath("$.lName").value("Bourne"))
                .andExpect(jsonPath("$.role").value("User"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(MockMvcResultMatchers.cookie().exists("token"));
    }


    @Test
    @DisplayName("Should return unauthorized if JWT token is expired")
    public void jwtTokenExpiredTest() throws Exception {
        String expiredJWT = dataGenerator.generateOutOfDateToken(account.getId()); // Implement this method to generate an expired token
        mockMvc.perform(get(ENDPOINT_URL)
                        .cookie(new MockCookie("token", expiredJWT)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should return unauthorized if JWT token is invalid")
    public void jwtTokenInvalidTest() throws Exception {
        // This is clearly not a valid JWT as it does not have the structure header.payload.signature
        String invalidJWT = "this.is.not.a.valid.jwt";

        mockMvc.perform(get(ENDPOINT_URL)
                        .cookie(new MockCookie("token", invalidJWT)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return unauthorized if no JWT token is present")
    public void noJwtTokenTest() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isUnauthorized());
    }


}
