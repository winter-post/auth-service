package com.devwinter.authservice.config.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {
    Long getId();
    String getUsername();
    String getPassword();
    String getEmail();
    String getPicture();
    String getProvider();
    List<? extends GrantedAuthority> getAuthorities();
    Map<String, Object> getAttributes();
    boolean isCertificated();
    void isCertificated(boolean isCertificated);
}
