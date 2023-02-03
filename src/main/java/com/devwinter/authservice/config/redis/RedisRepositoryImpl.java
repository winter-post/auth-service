package com.devwinter.authservice.config.redis;

import com.devwinter.authservice.config.jwt.TokenInfo;
import com.devwinter.authservice.config.redis.RedisRepository;
import com.devwinter.authservice.config.security.model.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${spring.redis.key.prefix}")
    private String redis_key_prefix;

    @Override
    public void saveRefreshToken(Authentication authentication, TokenInfo tokenInfo) {
        redisTemplate.opsForValue()
                     .set(getKey(authentication), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
    }

    @Override
    public String findRefreshToken(Authentication authentication) {
        return (String) redisTemplate.opsForValue().get(getKey(authentication));
    }

    @Override
    public void deleteRefreshToken(Authentication authentication) {
        if (redisTemplate.opsForValue().get(getKey(authentication)) != null) {
            redisTemplate.delete(getKey(authentication));
        }
    }

    private String getKey(Authentication authentication) {
        return String.format("%s: %s", redis_key_prefix, ((PrincipalUser) authentication.getPrincipal()).getEmail());
    }
}