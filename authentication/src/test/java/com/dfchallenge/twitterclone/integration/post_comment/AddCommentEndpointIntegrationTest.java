package com.dfchallenge.twitterclone.integration.post_comment;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.dao.PostCommentRepository;
import com.dfchallenge.twitterclone.dao.PostRepository;
import com.dfchallenge.twitterclone.data_generator.DataGenerator;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.service.PostCommentService;
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

import javax.xml.crypto.Data;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddCommentEndpointIntegrationTest {

    private MockMvc mockMvc;
    private AccountRepository accountRepository;
    private PostRepository postRepository;
    private PostCommentRepository postCommentRepository;
    private PostCommentService postCommentService;
    private DataGenerator dataGenerator;
    private JWTServices jwtServices;

    @Autowired
    public AddCommentEndpointIntegrationTest(JWTServices jwtServices,MockMvc mockMvc, AccountRepository accountRepository, PostRepository postRepository, PostCommentRepository postCommentRepository, PostCommentService postCommentService, DataGenerator dataGenerator) {
        this.mockMvc = mockMvc;
        this.jwtServices = jwtServices;
        this.accountRepository = accountRepository;
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
        this.postCommentService = postCommentService;
        this.dataGenerator = dataGenerator;
    }

    private String ENDPOINT_URL = "/post-comment/add-comment";
    private Account account;
    private String token;
    private List<Post> posts;

    @BeforeEach
    public void setup() {
        try {
            accountRepository.deleteAll();
            postRepository.deleteAll();
            postCommentRepository.deleteAll();

            account = dataGenerator.addAccountToDatabase();
            token = jwtServices.generateToken(account.getId());
            posts = dataGenerator.addPostsToDatabase(account);

            System.out.println("Setup completed successfully");
        } catch (Exception e) {
            System.out.println("Failed to complete pre-test setup");
            System.out.println(e.getMessage());

        }
    }

    @AfterEach
    public void cleanup(){
        accountRepository.deleteAll();
        postRepository.deleteAll();
        postCommentRepository.deleteAll();
    }

    @Test
    @DisplayName("Should successful add comment - return 200")
    public void successfulAddComment_return200() throws Exception {

        int postId = posts.get(0).getId();
        String commentContent = "this is my first comment";

        String requestBody = String.format("""
            {
                "postId": %d,
                "commentContent": "%s"
            }
            """, postId, commentContent);

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .cookie(new MockCookie("token", token))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        resultActions.andExpect(status().isOk());
    }


    @Test
    @DisplayName("Should fail to add comment due to no postId- return 400")
    public void failToAddCommentNoPostId_return400() throws Exception {

        String requestBody = """
            {
                "postId": ,
                "commentContent": "this is my first comment"
            }
            """;

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .cookie(new MockCookie("token", token))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail to add comment due to no text - return 400")
    public void failToAddCommentNoText_return400() throws Exception {

        int postId = posts.get(0).getId();

        String requestBody = String.format("""
            {
                "postId": %d,
                "commentContent": ""
            }
            """, postId);

        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_URL)
                .cookie(new MockCookie("token", token))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        resultActions.andExpect(status().isBadRequest());
    }


}
