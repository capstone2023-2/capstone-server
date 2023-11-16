package com.capstone.demo.service;

import com.capstone.demo.exception.AppException;
import com.capstone.demo.exception.ErrorCode;
import com.capstone.demo.jwt.JwtProvider;
import com.capstone.demo.model.SocialAccount;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.TokenDto;
import com.capstone.demo.model.dto.request.LoginRequest;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final BCryptPasswordEncoder encoder;

    public UserResponseDto join(UserRegisterDto userRegisterDto){
        userRepository.findByEmail(userRegisterDto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.EMAIL_DUPLICATED, "이미 존재하는 email입니다.");
                });

        userRepository.findByUsername(userRegisterDto.getUsername())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, "이미 존재하는 username입니다.");
                });

        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .username(userRegisterDto.getUsername())
                .password(encoder.encode(userRegisterDto.getPassword()))
                .posts(new ArrayList<>())
                .forums(new ArrayList<>())
                .comments(new ArrayList<>())
                .collections(new ArrayList<>())
                .socialAccount(new SocialAccount())
                .build();
        userRepository.save(user);

        return UserResponseDto.of(user);
    }

    @Transactional
    public TokenDto login(LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication); // 없을 때도 Token 발급은 제대로 됏음
        String authorities = getAuthorities(authentication);

        return generateToken(authentication.getName(), authorities);
    }

    @Transactional
    public TokenDto reissue(String requestAccessTokenInHeader, String requestRefreshToken) {
        String requestAccessToken = resolveToken(requestAccessTokenInHeader);

        Authentication authentication = jwtProvider.getAuthentication(requestAccessToken);
        String email = getPrincipal(requestAccessToken);

        String refreshTokenInRedis = redisService.getValues("RT:" + email);
        if (refreshTokenInRedis == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
        if(!jwtProvider.validateRefreshToken("RT:" + email, requestRefreshToken) || !refreshTokenInRedis.equals(requestRefreshToken)) {
            redisService.deleteValues("RT:" + email); // 탈취 가능성 -> 삭제
            return null; // -> 재로그인 요청
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        // 토큰 재발급 및 Redis 업데이트
        redisService.deleteValues("RT:" + email); // 기존 RT 삭제
        TokenDto tokenDto = jwtProvider.createToken(email, authorities);
        saveRefreshToken(email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Transactional
    public void logout(String requestAccessTokenInHeader) {
        String requestAccessToken = resolveToken(requestAccessTokenInHeader);
        String principal = getPrincipal(requestAccessToken);

        // Redis에 저장되어 있는 RT 삭제
        String refreshTokenInRedis = redisService.getValues(principal);
        if (refreshTokenInRedis != null) {
            redisService.deleteValues(principal);
        }

        // Redis에 로그아웃 처리한 AT 저장
        long expiration = jwtProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
        redisService.setValuesWithTimeout(requestAccessToken,
                "logout",
                expiration);
    }

    // 토큰 발급
    @Transactional
    public TokenDto generateToken(String email, String authorities) {
        // RT가 이미 있을 경우
        if(redisService.getValues("RT:" + email) != null) {
            redisService.deleteValues("RT:" + email); // 삭제
        }

        // AT, RT 생성 및 Redis에 RT 저장
        TokenDto tokenDto = jwtProvider.createToken(email, authorities);
        saveRefreshToken(email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    // RT를 Redis에 저장
    @Transactional
    public void saveRefreshToken(String email, String refreshToken) {
        redisService.setValuesWithTimeout("RT:" + email, // key
                refreshToken, // value
                jwtProvider.getTokenExpirationTime(refreshToken)); // timeout(milliseconds)
        log.info(jwtProvider.getTokenExpirationTime(refreshToken) + ": refresh token 저장될 때 만료시간 값");
    }

    // 권한 이름 가져오기
    public String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // AT로부터 email 추출
    public String getPrincipal(String requestAccessToken) {
        return jwtProvider.getAuthentication(requestAccessToken).getName();
    }

    // "Bearer {AT}"에서 {AT} 추출
    public String resolveToken(String requestAccessTokenInHeader) {
        if (requestAccessTokenInHeader != null && requestAccessTokenInHeader.startsWith("Bearer ")) {
            return requestAccessTokenInHeader.substring(7);
        }
        log.info("Access Token 을 받지 못함");
        return null;
    }
}
