package com.devwinter.authservice.presentation;

import com.devwinter.authservice.application.LogoutCommandFacade;
import com.devwinter.authservice.presentation.dto.BaseResponse;
import com.devwinter.authservice.presentation.dto.Logout;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthApiController {
    private final LogoutCommandFacade logoutCommandFacade;

    @GetMapping("/user/logout")
    public BaseResponse<Logout.Response> logout(@RequestHeader("User-Id") Long userId) {
        logoutCommandFacade.logout(userId);
        return Logout.Response.success(userId);
    }
}
