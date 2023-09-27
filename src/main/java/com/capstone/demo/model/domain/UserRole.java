package com.capstone.demo.model.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_USER"),
    GUEST("ROLE_USER");

    private final String value;
}
