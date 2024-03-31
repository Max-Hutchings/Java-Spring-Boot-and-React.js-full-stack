package com.dfchallenge.twitterclone.controller.Account;

import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.service.AccountService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@Import(AccountService.class)
@AutoConfigureMockMvc(addFilters = false)
public class SignupEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTServices jwtServices;

    @MockBean
    private CookieAdder cookieAdder;


    @MockBean
    private AccountService accountService;

    String ENDPOINT_URL = "/authentication/create-account";

    Account mockAccount = new Account("best_username_ever", "Jason", "Borne", "jason@gmail.com", "PassWord233##!",
            "USER");
    String jsonRequest = """
    {
        "username": "best_username_ever",
        "fName": "Jason",
        "lName": "Borne",
        "email": "jason@gmail.com",
        "password": "PassWord233##!",
        "role": "USER"
    }
    """;




    @Test
    @DisplayName("Should create account and return 201 created status")
    public void accountControllerSuccess_check201() throws Exception{
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated()); // Checking if the status is 201
    }

    @Test
    @DisplayName("Should create account and return username")
    public void accountControllerSuccess_checkUsername() throws Exception{
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(jsonPath("$.username").value("best_username_ever"));
    }

    @Test
    @DisplayName("Should create account and return email")
    public void accountControllerSuccess_checkEmail() throws Exception{
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(jsonPath("$.email").value("jason@gmail.com"));
    }

    @Test
    @DisplayName("Should create account and return a valid id")
    public void accountControllerSuccess_checkId() throws Exception {
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(jsonPath("$.id", Matchers.anything())); // Check that the id field exists and has a value
    }

    @Test
    @DisplayName("Should create account and return correct first name")
    public void accountControllerSuccess_checkFirstName() throws Exception {
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(jsonPath("$.fName").value("Jason")); // Check the fName field value
    }

    @Test
    @DisplayName("Should create account and return correct last name")
    public void accountControllerSuccess_checkLastName() throws Exception {
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(jsonPath("$.lName").value("Borne")); // Check the lName field value
    }

    @Test
    @DisplayName("Should create account and return correct role")
    public void accountControllerSuccess_checkRole()throws Exception{
        when(accountService.saveAccount(any(Account.class))).thenReturn(mockAccount);

        mockMvc.perform(post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(jsonPath("$.authorities[0].authority").value("USER"));

    }



}
