package com.devwinter.authservice.config.security.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

@Builder
public class FormLoginUser implements ProviderUser {

    private Long id;
    private String email;
    private String password;
    private String provider;

    @Override
    public Long getId() {
        return this.id;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPicture() {
        return null;
    }

    @Override
    public String getProvider() {
        return this.provider;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public boolean isCertificated() {
        return false;
    }

    @Override
    public void isCertificated(boolean isCertificated) {

    }
}
