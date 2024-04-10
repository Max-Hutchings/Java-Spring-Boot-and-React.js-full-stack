package com.dfchallenge.twitterclone.integration.post;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.dao.PostRepository;
import com.dfchallenge.twitterclone.data_generator.DataGenerator;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.crypto.Data;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAllPeepsEndpointIntegrationTest {


    private MockMvc mockMvc;
    private AccountService accountService;
    private PostService postService;
    private PostRepository postRepository;
    private AccountRepository accountRepository;
    private DataGenerator dataGenerator;

    @Autowired
    public GetAllPeepsEndpointIntegrationTest(MockMvc mockMvc, AccountRepository accountRepository, AccountService accountService, PostService postService, PostRepository postRepository, DataGenerator dataGenerator){
        this.mockMvc = mockMvc;
        this.accountService = accountService;
        this.postService = postService;
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.dataGenerator = dataGenerator;
    }

    private String ENDPOINT_URL = "/post/get-all-peeps";

    private Account account;


    @BeforeEach
    public void setup() {
           postRepository.deleteAll();
           accountRepository.deleteAll();

           account = dataGenerator.addAccountToDatabase();
           dataGenerator.addPostsToDatabase(account);

    }

    @AfterEach
    public void tearDown(){
        postRepository.deleteAll();
        accountRepository.deleteAll();
    }



    @Test
    @DisplayName("Successful call - return 200")
    public void successfulCall_return200() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(ENDPOINT_URL));

        resultActions.andExpect(status().isOk());

    }

    @Test
    @DisplayName("Successful call - should return posts")
    public void successfulCall_shouldReturnPosts() throws Exception {

        ResultActions resultActions = mockMvc.perform(get(ENDPOINT_URL));

        resultActions
                .andExpect(jsonPath("$[0].postContent").exists())
                .andExpect(jsonPath("$[1].postContent").exists())
                .andExpect(jsonPath("$[2].postContent").exists());
    }
}
