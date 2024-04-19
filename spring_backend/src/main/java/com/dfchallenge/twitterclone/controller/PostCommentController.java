package com.dfchallenge.twitterclone.controller;

import com.dfchallenge.twitterclone.entity.PostComment.PostComment;
import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.exceptions.InvalidPostCommentInputException;
import com.dfchallenge.twitterclone.exceptions.InvalidPostInputException;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.service.AccountService;
import com.dfchallenge.twitterclone.service.PostCommentService;
import com.dfchallenge.twitterclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/post-comment")
public class PostCommentController {


    private final AccountService accountService;
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final JWTServices jwtServices;

    @Autowired
    public PostCommentController(AccountService accountService, PostService postService, PostCommentService postCommentService, JWTServices jwtServices) {
        this.accountService = accountService;
        this.postService = postService;
        this.postCommentService = postCommentService;
        this.jwtServices = jwtServices;
    }

    @PostMapping("/add-comment")
    public ResponseEntity<?> addComment(@RequestBody Map<String, String> body){
        PostComment postComment;
        Account account = null;

//        Get user details from the security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();

            if(principal instanceof Account){
                account = (Account) principal;

            }
        }

        try{
            postComment = new PostComment(Integer.parseInt(body.get("postId")), body.get("commentContent"),
                    account.getId());
            postCommentService.savePostComment(postComment);
        }catch(InvalidPostCommentInputException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid post details", "error", e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "successfully saved post"));
    }

    @GetMapping("/peep-comments/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Integer postId){
        return ResponseEntity.status(HttpStatus.OK).body(postCommentService.getPostComments(postId));
    }
}
