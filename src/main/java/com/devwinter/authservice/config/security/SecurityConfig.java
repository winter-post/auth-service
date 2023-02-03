package com.devwinter.authservice.config.security;

import com.devwinter.authservice.config.security.filters.JsonUsernamePasswordAuthenticationFilter;
import com.devwinter.authservice.config.security.handler.LoginFailureHandler;
import com.devwinter.authservice.config.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        http
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // TODO: API Gateway가 만약 scale out 할 경우 동적으로 IP Voter를 사용해서 IP 검사를 해야할 것 같다.
//        http
//                .authorizeRequests()
//                .antMatchers("/**")
//                .access("hasIpAddress('192.168.0.2')");
        http
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll();

        http
                .addFilterBefore(authenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        JsonUsernamePasswordAuthenticationFilter authenticationFilter = new JsonUsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return authenticationFilter;
    }
}
