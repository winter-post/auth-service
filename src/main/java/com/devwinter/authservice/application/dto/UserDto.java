package com.devwinter.authservice.application.dto;

import com.devwinter.authservice.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long id;
    private String email;

    public static UserDto of(User user) {
        return UserDto.builder()
                      .id(user.getId())
                      .email(user.getEmail())
                      .build();
    }
}
