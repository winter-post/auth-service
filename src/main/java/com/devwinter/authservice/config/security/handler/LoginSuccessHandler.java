package com.devwinter.authservice.config.security.handler;

import antlr.Token;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        createJwtTokenResponse(response, tokenInfo);
    }

    private void createJwtTokenResponse(HttpServletResponse response, TokenInfo tokenInfo) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        objectMapper.writeValue(response.getWriter(), JwtTokenResponse.of(tokenInfo));
    }
}
