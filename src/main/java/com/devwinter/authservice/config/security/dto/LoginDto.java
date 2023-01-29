package com.devwinter.authservice.config.security.dto;


import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class LoginDto {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equals or grater than 8 characters")
    private String password;
}
