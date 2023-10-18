package com.capstone.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final SecretKey secret = Keys.hmacShaKeyFor(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());

    public static String getEmail(String token){
        return Jwts.parserBuilder().setSigningKey(JwtUtil.getSecret()).build()
                .parseClaimsJws(token).getBody().get("email", String.class);
    }

    public static boolean isExpired(String token){
        return Jwts.parserBuilder().setSigningKey(JwtUtil.getSecret()).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static String createToken(String email, long expirationMs){
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    public static SecretKey getSecret(){
        return secret;
    }
}
