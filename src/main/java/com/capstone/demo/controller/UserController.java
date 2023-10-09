package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.LoginRequest;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRegisterDto userRegisterDto) throws Exception {

        userService.join(userRegisterDto);
        return ResponseEntity.ok().body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        String token = userService.login(loginRequest);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> registerUser(@RequestBody UserRegisterDto userRegisterDto) throws Exception {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "user successfully registered!",
                        userService.registerUser(userRegisterDto)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getUser(@PathVariable Long id){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all users data successfully received!",
                        userService.getUser(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getUsers(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all users data successfully received!",
                        userService.getUsers()), HttpStatus.OK);
    }
}
