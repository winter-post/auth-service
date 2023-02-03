package com.devwinter.authservice.config.redis;


import com.devwinter.authservice.config.jwt.TokenInfo;
import org.springframework.security.core.Authentication;

public interface RedisRepository {
    void saveRefreshToken(String email, TokenInfo tokenInfo);
    void deleteRefreshToken(String email);
}