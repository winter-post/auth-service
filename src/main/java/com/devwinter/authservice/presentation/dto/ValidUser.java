package com.devwinter.authservice.presentation.dto;

import com.devwinter.authservice.application.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

public class ValidUser {
    @Getter
    @Builder
    public static class Response {
        private Long userId;

        public static BaseResponse<ValidUser.Response> success(UserDto userDto) {
            return BaseResponse.success(Response.builder()
                                                .userId(userDto.getId())
                                                .build(), "회원 인증에 성공하였습니다.");
        }
    }
}
