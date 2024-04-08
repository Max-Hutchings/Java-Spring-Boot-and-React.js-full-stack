package com.dfchallenge.postsservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthServiceRequester {

    private final WebClient webClient;

    public AuthServiceRequester(WebClient.Builder builder){
        this.webClient = builder.baseUrl("http://localhost:4000").build();

    }

    public Mono<Boolean> isUserValid(String jwtToken, String userId) {
        Map<String, String> requestBody = Map.of(
                "jwtToken", jwtToken,
                "userId", userId
        );

        return webClient.post()
                .uri("/authentication/validate")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Boolean.class);
    }



}
