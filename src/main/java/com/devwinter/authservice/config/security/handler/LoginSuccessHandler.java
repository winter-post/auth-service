package com.devwinter.authservice.config.security.handler;

import com.devwinter.authservice.config.security.JwtTokenProvider;
import com.devwinter.authservice.config.security.dto.JwtTokenResponse;
import com.devwinter.authservice.config.security.model.PrincipalUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        createJwtTokenResponse(response, tokenInfo, getUserId(authentication));
    }

    private void createJwtTokenResponse(HttpServletResponse response, TokenInfo tokenInfo, String userId) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        if(!Objects.isNull(userId)) {
            response.setHeader("User-Id", userId);
        }
        objectMapper.writeValue(response.getWriter(), JwtTokenResponse.of(tokenInfo));
    }

    private String getUserId(Authentication authentication) {
        if(authentication.getPrincipal() instanceof PrincipalUser) {
            return ((PrincipalUser) authentication.getPrincipal()).getUserId().toString();
        }
        return null;
    }
}
