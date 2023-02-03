package com.devwinter.authservice.config.security.dto;

import com.devwinter.authservice.presentation.dto.BaseResponse;
import com.devwinter.authservice.presentation.dto.FindUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class JwtTokenErrorResponse {
    private Long memberId;

    public static BaseResponse<JwtTokenErrorResponse> of(Long memberId) {
        return BaseResponse.success(new JwtTokenErrorResponse(memberId), "회원 조회에 성공하였습니다.");
    }
}
