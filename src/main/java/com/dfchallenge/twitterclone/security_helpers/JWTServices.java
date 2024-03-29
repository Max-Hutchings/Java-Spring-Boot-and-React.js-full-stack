package com.dfchallenge.twitterclone.security_helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Service
public class JWTServices {

    @Value("${jwt.secret}")
    private String secret;


    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Integer extractAccountId(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Integer.class);
    }

    private Date extractExpiration(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public String generateToken(Map<String, Object> extraClaims, String accountId){
        final int expirationTime = 1000 * 60 * 60 * 24 * 10;
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(accountId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, Integer accountId){
        final Integer idFromToken = extractAccountId(token);
        return (accountId.equals(idFromToken) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }




}
