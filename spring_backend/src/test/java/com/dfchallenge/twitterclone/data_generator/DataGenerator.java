package com.dfchallenge.twitterclone.data_generator;

import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.PostCommentService;
import com.dfchallenge.twitterclone.service.PostService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class DataGenerator {

    private final AccountService accountService;
    private final PostService postService;
    private final PostCommentService postCommentService;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    public DataGenerator(AccountService accountService, PostService postService, PostCommentService postCommentService){
        this.accountService = accountService;
        this.postService = postService;
        this.postCommentService = postCommentService;
    }

    public Account addAccountToDatabase(){
        Account account;
        account = new Account("not_quite_007", "Jason", "Bourne", "jason@gmail.com",
                "PassWord233##!", "USER");
        account = accountService.saveAccount(account);
        System.out.println(account.toString());
        return account;
    }


    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateOutOfDateToken(Integer accountId) {
        final int expirationTime = -1000;
        return Jwts
                .builder()
                .setSubject(Integer.toString(accountId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public List<Post> addPostsToDatabase(Account account){
        String[] postContents = {"This is a post", "This is another post", "This is yet another post"};

        List<Post> posts = new ArrayList<>();

        for (String content : postContents) {
            Post post = postService.savePost(new Post(content, account.getId()));
            posts.add(post);
        }

        return posts;
    }

    public List<PostComment> addCommentsToDatabase(Account account, Post post){
        String[] commentTexts = {"my first comment", "my second comment", "my third comment"};

        List<PostComment> postComments = new ArrayList<>();

        for(String commentText : commentTexts){
            PostComment postComment = new PostComment(post.getId(), commentText, account.getId());
            postComment = postCommentService.savePostComment(postComment);
            postComments.add(postComment);
        }

        return postComments;
    }
}
