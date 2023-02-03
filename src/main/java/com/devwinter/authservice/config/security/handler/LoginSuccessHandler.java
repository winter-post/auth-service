package com.devwinter.authservice.config.security.handler;

import com.devwinter.authservice.config.jwt.JwtTokenProvider;
import com.devwinter.authservice.config.jwt.TokenInfo;
import com.devwinter.authservice.config.redis.RedisRepository;
import com.devwinter.authservice.config.security.dto.JwtTokenErrorResponse;
import com.devwinter.authservice.config.security.dto.JwtTokenResponse;
import com.devwinter.authservice.config.security.model.PrincipalUser;
import com.devwinter.authservice.presentation.dto.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenInfo tokenInfo = null;
        try {
            tokenInfo = jwtTokenProvider.generateToken(authentication);

            // redis refresh token save
            redisRepository.saveRefreshToken(authentication, tokenInfo);

            // success response
            successResponse(response, tokenInfo, getUserId(authentication));
        } catch (Exception e) {
            log.error("LoginSuccessHandler onAuthenticationSuccess:", e);
            // fail response
            failResponse(response, (tokenInfo != null) ? tokenInfo.getUserId() : -1L);
        }
    }

    private void successResponse(HttpServletResponse response, TokenInfo tokenInfo, String userId) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        if(!Objects.isNull(userId)) {
            response.setHeader("User-Id", userId);
        }

        BaseResponse<JwtTokenResponse> jwtTokenResponse = JwtTokenResponse.success(tokenInfo);
        objectMapper.writeValue(response.getWriter(), jwtTokenResponse.getBody());
    }

    private void failResponse(HttpServletResponse response, Long userId) throws IOException {
        BaseResponse<JwtTokenErrorResponse> errorResponse = JwtTokenErrorResponse.of(userId);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    private String getUserId(Authentication authentication) {
        if(authentication.getPrincipal() instanceof PrincipalUser) {
            return ((PrincipalUser) authentication.getPrincipal()).getUserId().toString();
        }
        return null;
    }
}
