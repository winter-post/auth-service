package com.devwinter.authservice.config.jwt;

import com.devwinter.authservice.config.security.model.PrincipalUser;
import com.devwinter.authservice.utils.AuthenticationUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final Long accessTokenValidTime;
    private final Long refreshTokenValidTime;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                            @Value("${jwt.access-token-expiration-time}") Long accessTokenValidTime,
                            @Value("${jwt.refresh-token-expiration-time}") Long refreshTokenValidTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidTime = accessTokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
    }

    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                                           .stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .collect(Collectors.joining(","));

        PrincipalUser principalUser = AuthenticationUtil.getPrincipalUser(authentication);

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + accessTokenValidTime);
        String accessToken = Jwts.builder()
                                 .setSubject(principalUser.getEmail())
                                 .setAudience(principalUser.getUserId()
                                                           .toString())
                                 .claim("auth", authorities)
                                 .setExpiration(accessTokenExpiresIn)
                                 .signWith(key, SignatureAlgorithm.HS256)
                                 .compact();

        // Refresh Token 생성
        Date refreshTokenExpiresIn = new Date(now + refreshTokenValidTime);
        String refreshToken = Jwts.builder()
                                  .setExpiration(refreshTokenExpiresIn)
                                  .signWith(key, SignatureAlgorithm.HS256)
                                  .compact();

        return TokenInfo.builder()
                        .grantType("Bearer")
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .userId(principalUser.getUserId())
                        .refreshTokenExpirationTime(this.refreshTokenValidTime)
                        .build();
    }

}