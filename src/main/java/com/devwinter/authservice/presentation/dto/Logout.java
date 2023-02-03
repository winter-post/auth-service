package com.devwinter.authservice.presentation.dto;

import lombok.Builder;
import lombok.Getter;

public class Logout {
    @Getter
    @Builder
    public static class Response {
        private Long userId;

        public static BaseResponse<Logout.Response> success(Long userId) {
            return BaseResponse.success(Logout.Response.builder()
                                                       .userId(userId)
                                                       .build(), "로그아웃에 성공하였습니다.");
        }
    }
}
