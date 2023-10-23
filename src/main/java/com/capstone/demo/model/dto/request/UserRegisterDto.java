package com.capstone.demo.model.dto.request;

import lombok.Getter;

@Getter
public class UserRegisterDto {

    private String email;
    private String password;
    private String checkPassword;
    private String username;
}
