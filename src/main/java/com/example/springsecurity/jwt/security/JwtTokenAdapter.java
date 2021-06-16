package com.example.springsecurity.jwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Данный класс нужен, чтобы указать в какой
 *  момент будет отрабатывать filter на HTTP запросы
 * Можно сказать, что он конфигуриует когда мы будет проверять токен в запросе.
 * https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenFilter filter;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
