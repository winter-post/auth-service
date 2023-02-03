package com.devwinter.authservice.config.security.dto;

import com.devwinter.authservice.config.jwt.TokenInfo;
import com.devwinter.authservice.presentation.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenResponse {
    private String accessToken;
    private String grantType;
    private String refreshToken;

    public static BaseResponse<JwtTokenResponse> success(TokenInfo jwtTokenInfoDto) {
        return BaseResponse.success(JwtTokenResponse.builder()
                                                    .accessToken(jwtTokenInfoDto.getAccessToken())
                                                    .grantType(jwtTokenInfoDto.getGrantType())
                                                    .refreshToken(jwtTokenInfoDto.getRefreshToken())
                                                    .build(), "로그인에 성공하였습니다.");
    }
}