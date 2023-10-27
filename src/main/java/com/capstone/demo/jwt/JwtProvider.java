package com.capstone.demo.jwt;

import com.capstone.demo.model.dto.TokenDto;
import com.capstone.demo.service.CustomUserDetailsService;
import com.capstone.demo.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final RedisService redisService;
    private static final Long ACCESS_TOKEN_EXPIRATION_MS = 1000 * 60 * 30L; // 30분
    private static final Long REFRESH_TOKEN_EXPIRATION_MS = 1000 * 60 * 60 * 24 * 7L; // 7일
    private static final String BEARER_TYPE = "Bearer";
    private static final SecretKey secret = Keys.hmacShaKeyFor(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());

    @Transactional
    public TokenDto createToken(String email, String authorities){
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("role", authorities);
        Long now = System.currentTimeMillis();

        String accessToken = Jwts.builder()
                .setSubject("access-token")
                .setClaims(claims)
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRATION_MS))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject("refresh-token")
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRATION_MS))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).get("email").toString();
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }

    public Claims getClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) { // Access Token
            return e.getClaims();
        }
    }

    public Long getTokenExpirationTime(String token) {
        return getClaims(token).getExpiration().getTime();
    }

    public boolean validateRefreshToken(String key, String refreshToken){
        try {
            if(redisService.getValues(key)==null){
                log.error("refresh token 이 null 로 들어온대 " + refreshToken);
                return false;
            }

            if (redisService.getValues(key).equals("delete")) { // 회원 탈퇴했을 경우
                return false;
            }
            log.info("refresh token 확인: " + refreshToken);
            Jwts.parserBuilder()
                    .setSigningKey(getSecret())
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature.", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        } catch (NullPointerException e){
            log.error("JWT Token is empty.", e);
            log.error("refresh token 없음?: " + refreshToken);
        }
        return false;
    }

    // Filter에서 사용
    public boolean validateAccessToken(String accessToken) {
        try {
            if (redisService.getValues(accessToken) != null // NPE 방지
                    && redisService.getValues(accessToken).equals("logout")) { // 로그아웃 했을 경우
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(getSecret())
                    .build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch(ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 재발급 검증 API에서 사용
    public boolean validateAccessTokenOnlyExpired(String accessToken) {
        try {
            return getClaims(accessToken)
                    .getExpiration()
                    .before(new Date());
        } catch(ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(JwtProvider.getSecret()).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static SecretKey getSecret(){
        return secret;
    }
}
