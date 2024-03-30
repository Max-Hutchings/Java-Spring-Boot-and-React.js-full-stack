package com.dfchallenge.twitterclone.security_helpers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieAdder {

    public HttpServletResponse addTokenToCookie(String jwtToken, HttpServletResponse response){
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        String cookieHeader = String.format("%s=%s; Path=%s; HttpOnly; Secure; SameSite=None",
                cookie.getName(), cookie.getValue(), cookie.getPath());

        response.addCookie(cookie);
        return response;
    }
}
