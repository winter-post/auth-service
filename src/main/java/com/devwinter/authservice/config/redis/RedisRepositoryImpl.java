package com.devwinter.authservice.config.redis;

import com.devwinter.authservice.config.jwt.TokenInfo;
import com.devwinter.authservice.config.security.model.PrincipalUser;
import com.devwinter.authservice.domain.exception.UserErrorCode;
import com.devwinter.authservice.domain.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${spring.redis.key.prefix}")
    private String redis_key_prefix;

    @Override
    public void saveRefreshToken(String email, TokenInfo tokenInfo) {
        redisTemplate.opsForValue()
                     .set(createKey(email), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
    }

    private boolean existRefreshToken(String email) {
        return Objects.nonNull((String) redisTemplate.opsForValue().get(createKey(email)));
    }

    @Override
    public void deleteRefreshToken(String email) {
        if (!existRefreshToken(email)) {
            throw new UserException(UserErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
        redisTemplate.delete(createKey(email));
    }
    private String createKey(String email) {
        return String.format("%s: %s", redis_key_prefix, email);
    }
}