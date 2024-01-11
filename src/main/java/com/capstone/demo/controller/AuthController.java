package com.capstone.demo.controller;

import com.capstone.demo.model.dto.TokenDto;
import com.capstone.demo.model.dto.request.LoginRequest;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "인증 및 인가 기능", description = "AuthController")
public class AuthController {

    private final AuthService authService;

    private final long COOKIE_EXPIRATION = 7776000; // 90일

    @Operation(summary = "회원가입", description = "이메일(email), 비밀번호(password)와 사용자 이름(username)을 이용하여 회원가입을 합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공. 사용자 정보 반환",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    }, tags = "인증 및 인가 기능")
    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(authService.join(userRegisterDto));
    }

    @Operation(summary = "로그인", description = "이메일(email)과 비밀번호(password)를 이용하여 로그인을 합니다.", responses = {
            @ApiResponse(description = "로그인 성공. 헤더의 Authorization에 access-token. 헤더의 Cookie에 refresh-token")
    }, tags = "인증 및 인가 기능")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        // User 등록 및 Refresh Token 저장
        TokenDto tokenDto = authService.login(loginRequest);

        // RT 저장
        HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
                .maxAge(COOKIE_EXPIRATION)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
        log.info(String.valueOf(httpCookie));

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
                // AT 저장
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .build();
    }

    @Operation(summary = "토큰 재발급", description = "쿠키의 refresh-token을 requestRefreshToken으로 넣고 "
            + "헤더의 Authorization 을 requestAccessToken으로 넣어야 합니다", responses = {
            @ApiResponse(description = "토큰 재발급 성공. 헤더의 Authorization에 access-token. 헤더의 Cookie에 refresh-token")
    }, tags = "인증 및 인가 기능")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
                                     @RequestHeader("Authorization") String requestAccessToken) {
        TokenDto reissuedTokenDto = authService.reissue(requestAccessToken, requestRefreshToken);

        if (reissuedTokenDto != null) { // 토큰 재발급 성공
            // RT 저장
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
                    .maxAge(COOKIE_EXPIRATION)
                    .httpOnly(true)
                    .secure(false)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    // AT 저장
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
                    .build();

        } else { // Refresh Token 탈취 가능성
            // Cookie 삭제 후 재로그인 유도
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();
        }
    }

    @Operation(summary = "로그아웃", description = "헤더의 Authorization을 requestAccessToken으로 넣어 요청합니다.", responses = {
            @ApiResponse(description = "로그아웃 성공")
    }, tags = "인증 및 인가 기능")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
        authService.logout(requestAccessToken);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }
}
