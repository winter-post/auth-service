package com.devwinter.authservice.config.security.converters;

import com.devwinter.authservice.config.security.model.FormLoginUser;
import com.devwinter.authservice.config.security.model.ProviderUser;
import com.devwinter.authservice.domain.User;

public class UserDetailsProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (providerUserRequest.user() == null) {
            return null;
        }

        User user = providerUserRequest.user();
        return FormLoginUser.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .email(user.getEmail())
                            .provider("none")
                            .build();
    }
}
