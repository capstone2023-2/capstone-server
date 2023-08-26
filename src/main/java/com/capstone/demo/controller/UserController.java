package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> registerUser(@RequestBody UserRegisterDto userRegisterDto){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "user successfully registered!",
                        userService.registerUser(userRegisterDto)), HttpStatus.CREATED);
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
