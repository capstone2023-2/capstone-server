package com.capstone.demo.model.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {

    GOOGLE("google", (attributes) -> {
        return User.of(
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                "google",
                "google_"+ attributes.get("sub")
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, User> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, User> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static User extract(String registrationId, Map<String, Object> attributes) {

        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
