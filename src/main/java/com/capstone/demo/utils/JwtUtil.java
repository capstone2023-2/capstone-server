package com.capstone.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    private static final SecretKey secret = Keys.hmacShaKeyFor(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
    private static final Long ACCESS_TOKEN_EXPIRATION_MS = 1000 * 60 * 30L; // 30분

    public static String getEmail(String token){
        return Jwts.parserBuilder().setSigningKey(JwtUtil.getSecret()).build()
                .parseClaimsJws(token).getBody().get("email", String.class);
    }

    public static boolean isExpired(String token){
        log.info("front에서 받은 token: {}", token);
        return Jwts.parserBuilder().setSigningKey(JwtUtil.getSecret()).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static String createAccessToken(String email){
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MS))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    public static SecretKey getSecret(){
        return secret;
    }
}
