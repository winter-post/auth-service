package com.devwinter.authservice.application;

import com.devwinter.authservice.application.dto.UserDto;
import com.devwinter.authservice.domain.User;
import com.devwinter.authservice.domain.exception.UserException;
import com.devwinter.authservice.domain.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devwinter.authservice.domain.exception.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserQueryFacade {

    private final UserQueryRepository userQueryRepository;

    public UserDto findUserByEmail(String email) {
        User user = userQueryRepository.findByEmail(email)
                                       .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return UserDto.of(user);
    }

    public UserDto findUserById(Long userId) {
        User user = userQueryRepository.findById(userId)
                                       .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return UserDto.of(user);
    }
}
