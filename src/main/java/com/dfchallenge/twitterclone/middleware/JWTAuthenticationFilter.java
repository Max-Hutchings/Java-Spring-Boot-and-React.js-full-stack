package com.dfchallenge.twitterclone.middleware;

import com.dfchallenge.twitterclone.entity.Account;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.dfchallenge.twitterclone.service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    private AccountService accountService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwt = extractJwtFromRequest(request);
        final Integer accountId;

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        accountId = jwtServices.extractAccountId(jwt);
        if (accountId != null && SecurityContextHolder.getContext().getAuthentication() == null){

//            try{
                Account account = accountService.getAccountById(accountId);
                if (jwtServices.isTokenValid(jwt, account.getId())){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            account,
                            null,
                            account.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
//            }catch(Exception e){
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//                response.setContentType("application/json");
//                String jsonResponse = "{\"message\": \"Invalid authentication\"}";
//                response.getWriter().write(jsonResponse);
//                return;
//            }

        }
        filterChain.doFilter(request, response);
    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
