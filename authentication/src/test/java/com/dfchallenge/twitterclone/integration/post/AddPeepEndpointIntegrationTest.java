package com.dfchallenge.twitterclone.integration.post;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.dao.PostRepository;
import com.dfchallenge.twitterclone.data_generator.DataGenerator;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.PostService;
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
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddPeepEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DataGenerator dataGenerator;

    private Account account;
    private String token;
    private String ENDPOINT_URL = "/post/add-peep";


    @BeforeEach
    public void setup() {
        accountRepository.deleteAll();
        postRepository.deleteAll();

        account = dataGenerator.addAccountToDatabase();
        token = jwtServices.generateToken(account.getId());
    }

    @AfterEach
    public void cleanup(){
        accountRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("Successful Post - Returns 200 OK")
    public void whenValidPost_thenPostSuccess() throws Exception {
        String jsonRequest = """
            {
                "postContent": "This is a test post"
            }
            """;

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new MockCookie("token", token))
                .content(jsonRequest));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Failed Post - Returns 400 Bad Request")
    public void whenInvalidPost_thenBadRequestResponse() throws Exception {
        String jsonRequest = """
            {
                "postContent": ""
            }
            """;

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new MockCookie("token", token))
                .content(jsonRequest));

        resultActions.andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Failed Post - Returns 401 Unauthorized")
    public void whenInvalidToken_thenUnauthorizedResponse() throws Exception {
        String jsonRequest = """
            {
                "postContent": "This is a test post"
            }
            """;

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new MockCookie("token", "invalidToken"))
                .content(jsonRequest));

        resultActions.andExpect(status().isUnauthorized());
    }
}
