package com.devwinter.authservice.config.security.service;

import com.devwinter.authservice.config.security.converters.ProviderUserConverter;
import com.devwinter.authservice.config.security.converters.ProviderUserRequest;
import com.devwinter.authservice.config.security.model.ProviderUser;
import lombok.Getter;

@Getter
public abstract class AbstractOAuth2UserService {

    private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    public AbstractOAuth2UserService(ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter) {
        this.providerUserConverter = providerUserConverter;
    }

    protected ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.converter(providerUserRequest);
    }
}
