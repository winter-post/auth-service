package com.devwinter.authservice.config.redis;


import com.devwinter.authservice.config.jwt.TokenInfo;
import org.springframework.security.core.Authentication;

public interface RedisRepository {
    void saveRefreshToken(Authentication authentication, TokenInfo tokenInfo);
    String findRefreshToken(Authentication authentication);
    void deleteRefreshToken(Authentication authentication);
}