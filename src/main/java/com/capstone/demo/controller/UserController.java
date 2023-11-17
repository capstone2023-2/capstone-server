package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.LoginRequest;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "사용자 기능", description = "UserController")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일(email), 비밀번호(password)와 사용자 이름(username)을 이용하여 회원가입을 합니다."
            , tags = "사용자 기능")
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRegisterDto userRegisterDto) {

        userService.join(userRegisterDto);
        return ResponseEntity.ok().body("user successfully registered!");
    }

    @Operation(summary = "로그인", description = "이메일(email)과 비밀번호(password)를 이용하여 로그인을 합니다.", responses = {
            @ApiResponse(description = "로그인 성공 -> 토큰 반환")
    }, tags = "사용자 기능")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        String token = userService.login(loginRequest);
        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "사용자 조회", description = "한 명의 사용자 정보를 조회합니다.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    }, tags = "사용자 기능")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<UserResponseDto>> getUser(@PathVariable Long id) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "user data successfully received!",
                        userService.getUser(id)), HttpStatus.OK);
    }

    @Operation(summary = "전체 사용자 조회", description = "등록된 모든 사용자 정보를 조회합니다.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))
            )
    }, tags = "사용자 기능")
    @GetMapping
    public ResponseEntity<BaseResponseDto<List<UserResponseDto>>> getUsers() {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all users data successfully received!",
                        userService.getUsers()), HttpStatus.OK);
    }
}