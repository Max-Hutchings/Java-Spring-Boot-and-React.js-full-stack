package com.dfchallenge.twitterclone.controller;


import com.dfchallenge.twitterclone.entity.account.Account;
import com.dfchallenge.twitterclone.service.PostService;
import com.dfchallenge.twitterclone.service.PostServiceImpl;
import com.dfchallenge.twitterclone.entity.post.Post;
import com.dfchallenge.twitterclone.exceptions.InvalidPostInputException;
import com.dfchallenge.twitterclone.security_helpers.CookieAdder;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    private final CookieAdder cookieAdder;
    private final JWTServices jwtServices;
    private final PostService postService;


    @Autowired
    public PostController(CookieAdder cookieAdder, JWTServices jwtServices, PostService postService){
        this.cookieAdder = cookieAdder;
        this.jwtServices = jwtServices;
        this.postService = postService;
    }

    @PostMapping("/add-peep")
    public ResponseEntity<?> addPost(@RequestBody Map<String, String> body) {
        Post post;
        Account account = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Account) {
                account = (Account) principal;
            } else {
                System.out.println("couldnt find the account");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "failed to get account"));
            }

            try {
                post = new Post(body.get("postContent"), account.getId());
                post = postService.savePost(post);
            } catch (InvalidPostInputException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "failed to build and save post", "error", e.getMessage()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "successfully post"));
        }else{
            System.out.println("Called final resort");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "failed to get account"));
        }
    }


    @GetMapping("/get-all-peeps")
    public ResponseEntity<?> getAllPosts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }


}
