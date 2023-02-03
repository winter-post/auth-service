package com.devwinter.authservice.utils;

import com.devwinter.authservice.config.security.model.PrincipalUser;
import org.springframework.security.core.Authentication;

public class AuthenticationUtil {
    public static PrincipalUser getPrincipalUser(Authentication authentication) {
        return (PrincipalUser) authentication.getPrincipal();
    }
}
