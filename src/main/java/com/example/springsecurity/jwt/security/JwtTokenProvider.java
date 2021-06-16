package com.example.springsecurity.jwt.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * Данный класс является поставщиков возможностей для работы с JWT токенами,
 * то есть в нем находится логика генерации и валидации.
 * Можно сказать, что это сервис для работы с токенами.
 */
@Component
@PropertySource("classpath:token.properties")
public class JwtTokenProvider {
    private static final String BEARER_START = "Bearer";

    @Value("${jwt.expiredTime}")
    private long expiredTime;
    @Value("${jwt.secret}")
    private String secret;

    private UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String subject) {
        Date now = new Date();

        return Jwts.builder()
                .setIssuer(secret)
                .setSubject(subject)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .compact();
    }

    public String regenerateToken(String expiredToken) {
        String username = parseToken(expiredToken).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return generateToken(userDetails.getUsername());
    }

    public String resolveToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader("Authorization");
        String token = null;
        if (authenticationHeader != null) {
            if (!authenticationHeader.startsWith(BEARER_START)) {
                throw new JwtAuthException("Is not bearer token: " + authenticationHeader);
            }
            token = authenticationHeader.replace(BEARER_START + " ", "");
        }
        return token;
    }

    public void checkTokenOnValid(final String token) {
        try {
            parseToken(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException(token, e);
        }
    }

    public Authentication getAuthentication(final String token) {
        Jws<Claims> claimsJws = parseToken(token);
        String username = claimsJws.getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "",
                userDetails.getAuthorities());
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parser().parseClaimsJws(token);
    }

    public String getUsername(HttpServletRequest request) {
        String token = resolveToken(request);
        return parseToken(token).getBody().getSubject();
    }
}
