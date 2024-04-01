package com.dfchallenge.twitterclone.middleware;

import com.dfchallenge.twitterclone.exceptions.NoJWTException;
import com.dfchallenge.twitterclone.security_helpers.JWTServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestPath = new UrlPathHelper().getPathWithinApplication(request);
        if (isPathExcluded(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }



        try {
            String jwt = extractJwtFromRequest(request);
            final Integer accountId;

            if (jwt == null || jwt.isEmpty()) {
                throw new NoJWTException();
            }

            accountId = jwtServices.extractAccountId(jwt);
            if (accountId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(Integer.toString(accountId));
                if (jwtServices.isTokenValid(jwt, accountId)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            System.out.println("JWT Expired");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
        } catch(MalformedJwtException e){
            System.out.println("JWT Malformed");
            SecurityContextHolder.getContext().setAuthentication(null);
            Map<String, String> bodyMessage = Map.of("message", "malformed jwt", "error", e.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(bodyMessage));
        } catch(NoJWTException e){
            System.out.println("No JWT");

            SecurityContextHolder.getContext().setAuthentication(null);
            Map<String, String> bodyMessage = Map.of("message", "no jwt found", "error", e.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(bodyMessage));

        }
    }


        private String extractJwtFromRequest (HttpServletRequest request){
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("token".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
            return null;
        }

    private boolean isPathExcluded(String path) {
        // Define the paths to exclude
        List<String> excludedPaths = List.of(
                "/authentication/create-account",
                "/authentication/login",
                "/authentication/logout"
                // Add more paths as needed
        );

        return excludedPaths.contains(path);
    }
    }
