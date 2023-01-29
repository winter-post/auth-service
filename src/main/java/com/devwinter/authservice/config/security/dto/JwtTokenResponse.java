package com.devwinter.authservice.config.security.dto;

import com.devwinter.authservice.config.security.handler.TokenInfo;
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

    public static JwtTokenResponse of(TokenInfo jwtTokenInfoDto) {
        return JwtTokenResponse.builder()
                               .accessToken(jwtTokenInfoDto.getAccessToken())
                               .grantType(jwtTokenInfoDto.getGrantType())
                               .refreshToken(jwtTokenInfoDto.getRefreshToken())
                               .build();
    }
}