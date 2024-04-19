package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.dao.PostCommentRepository;
import com.dfchallenge.twitterclone.dao.PostRepository;
import com.dfchallenge.twitterclone.data_generator.DataGenerator;
import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import com.dfchallenge.twitterclone.entity.PostComment.PostCommentDTO;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.entity.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostCommentsServiceTest {

    private Account account;
    private List<Post> posts;
    private List<PostComment> postComments;
    private DataGenerator dataGenerator;
    private PostCommentService postCommentService;
    private AccountRepository accountRepository;
    private PostRepository postRepository;
    private PostCommentRepository postCommentRepository;

    @Autowired
    public  PostCommentsServiceTest(DataGenerator dataGenerator, PostCommentService postCommentService, AccountRepository accountRepository, PostRepository postRepository, PostCommentRepository postCommentRepository){
        this.dataGenerator = dataGenerator;
        this.postCommentService = postCommentService;
        this.accountRepository = accountRepository;
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @BeforeEach
    public void setup(){
        accountRepository.deleteAll();
        postRepository.deleteAll();
        postCommentRepository.deleteAll();


        account = dataGenerator.addAccountToDatabase();
        posts = dataGenerator.addPostsToDatabase(account);
        postComments = dataGenerator.addCommentsToDatabase(account, posts.get(0));
    }

    @Test
    @DisplayName("Should return 3 post comments")
    public void successfulGetAllPosts(){
        List<PostCommentDTO> postComments = postCommentService.getPostComments(posts.get(0).getId());
        assertEquals(3, postComments.size());
    }

    @Test
    @DisplayName("Should return valid details in the first returned comment")
    public void successfulGetPostDetails(){
        List<PostCommentDTO> postComments = postCommentService.getPostComments(posts.get(0).getId());

        if (!postComments.isEmpty()) {
            PostCommentDTO firstComment = postComments.get(0);
            System.out.println(firstComment.toString());
            assertNotNull(firstComment.getId(), "Comment ID should not be null");
            assertNotNull(firstComment.getPostId(), "Post ID should not be null");
            assertNotNull(firstComment.getCommentContent(), "Comment content should not be null");
            assertNotNull(firstComment.getAccountId(), "Account ID should not be null");
            assertNotNull(firstComment.getAccountUsername(), "Username should not be null");
            assertNotNull(firstComment.getCreatedDate(), "Creation date should not be null");
        }
    }
}
