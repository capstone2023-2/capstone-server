package com.capstone.demo.model.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    USER("ROLE_USER"),
    GUEST("ROLE_GUEST");

    private final String value;
}
