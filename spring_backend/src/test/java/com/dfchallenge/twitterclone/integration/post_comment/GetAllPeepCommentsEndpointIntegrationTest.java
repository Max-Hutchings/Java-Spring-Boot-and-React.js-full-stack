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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAllPeepCommentsEndpointIntegrationTest {

    private MockMvc mockMvc;
    private AccountRepository accountRepository;
    private PostRepository postRepository;
    private PostCommentRepository postCommentRepository;
    private PostCommentService postCommentService;
    private DataGenerator dataGenerator;
    private JWTServices jwtServices;

    @Autowired
    public GetAllPeepCommentsEndpointIntegrationTest(JWTServices jwtServices,MockMvc mockMvc, AccountRepository accountRepository,
             PostRepository postRepository, PostCommentRepository postCommentRepository, PostCommentService postCommentService, DataGenerator dataGenerator) {
        this.mockMvc = mockMvc;
        this.jwtServices = jwtServices;
        this.accountRepository = accountRepository;
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
        this.postCommentService = postCommentService;
        this.dataGenerator = dataGenerator;
    }


    private Account account;
    private String token;
    private List<Post> posts;
    private Post post;
    private String endpoint_url;

    @BeforeEach
    public void setup() {
        try {
            accountRepository.deleteAll();
            postRepository.deleteAll();
            postCommentRepository.deleteAll();

            account = dataGenerator.addAccountToDatabase();
            token = jwtServices.generateToken(account.getId());
            posts = dataGenerator.addPostsToDatabase(account);
            post = posts.get(0);
            endpoint_url ="/post-comment/peep-comments/" + post.getId();
            dataGenerator.addCommentsToDatabase(account, post);

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
    @DisplayName("Should successfully get comments - return 200")
    public void successfulGetComments_return200() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(endpoint_url)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new MockCookie("token", token)));

        resultActions
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Should successfuly get comments - return comments")
    public void successfulGetComments_returnComments() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(endpoint_url)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new MockCookie("token", token)));

        resultActions
                .andExpect(jsonPath("$[0].commentContent").value("my first comment"))
                .andExpect(jsonPath("$[1].commentContent").value("my second comment"))
                .andExpect(jsonPath("$[2].commentContent").value("my third comment"));

    }
}
