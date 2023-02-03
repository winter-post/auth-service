package com.devwinter.authservice.presentation.dto;

import com.devwinter.authservice.application.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

public class FindUser {

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String email;

        public static BaseResponse<FindUser.Response> success(UserDto userDto) {
            return BaseResponse.success(Response.builder()
                                                .id(userDto.getId())
                                                .email(userDto.getEmail())
                                                .build(), "회원 조회에 성공하였습니다.");
        }
    }
}
