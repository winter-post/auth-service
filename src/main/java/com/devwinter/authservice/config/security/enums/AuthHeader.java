package com.devwinter.authservice.config.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor

public enum AuthHeader {
    USER_ID("User-Id")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
