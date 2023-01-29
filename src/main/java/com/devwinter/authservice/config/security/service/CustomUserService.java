package com.devwinter.authservice.config.security.service;

import com.devwinter.authservice.config.security.converters.ProviderUserConverter;
import com.devwinter.authservice.config.security.converters.ProviderUserRequest;
import com.devwinter.authservice.config.security.model.PrincipalUser;
import com.devwinter.authservice.config.security.model.ProviderUser;
import com.devwinter.authservice.domain.User;
import com.devwinter.authservice.domain.repository.UserQueryRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService extends AbstractOAuth2UserService implements UserDetailsService {

    private final UserQueryRepository userQueryRepository;

    public CustomUserService(UserQueryRepository userQueryRepository,
                             ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter) {
        super(providerUserConverter);
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userQueryRepository.findByEmail(email)
                                       .orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(user);
        ProviderUser providerUser = super.providerUser(providerUserRequest);

        return new PrincipalUser(providerUser);
    }
}
