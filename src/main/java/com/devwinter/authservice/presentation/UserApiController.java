package com.devwinter.authservice.presentation;

import com.devwinter.authservice.application.UserQueryFacade;
import com.devwinter.authservice.application.dto.UserDto;
import com.devwinter.authservice.presentation.dto.BaseResponse;
import com.devwinter.authservice.presentation.dto.FindUser;
import com.devwinter.authservice.presentation.dto.ValidUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserApiController {
    private final UserQueryFacade userQueryFacade;

    @GetMapping("/{userId}/valid")
    public BaseResponse<ValidUser.Response> getUserValid(@PathVariable Long userId) {
        UserDto userDto = userQueryFacade.findUserById(userId);
        return ValidUser.Response.success(userDto);
    }
}
