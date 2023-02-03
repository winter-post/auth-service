package com.devwinter.authservice.application;

import com.devwinter.authservice.config.redis.RedisRepository;
import com.devwinter.authservice.domain.User;
import com.devwinter.authservice.domain.exception.UserException;
import com.devwinter.authservice.domain.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devwinter.authservice.domain.exception.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LogoutCommandFacade {
    private final UserQueryRepository userQueryRepository;
    private final RedisRepository redisRepository;

    public void logout(Long userId) {
        User user = userQueryRepository.findById(userId)
                                       .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        redisRepository.deleteRefreshToken(user.getEmail());
    }
}
