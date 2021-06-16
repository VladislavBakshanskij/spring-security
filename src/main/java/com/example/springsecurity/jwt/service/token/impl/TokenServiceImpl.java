package com.example.springsecurity.jwt.service.token.impl;

import com.example.springsecurity.jwt.controller.auth.TokenResponseDto;
import com.example.springsecurity.jwt.model.Token;
import com.example.springsecurity.jwt.model.User;
import com.example.springsecurity.jwt.repository.TokenRepository;
import com.example.springsecurity.jwt.repository.UserRepository;
import com.example.springsecurity.jwt.security.JwtTokenProvider;
import com.example.springsecurity.jwt.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public TokenResponseDto save(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        Token token = new Token()
                .setToken(tokenProvider.generateToken(user.getUsername()));
        user.getTokens().add(token);
        tokenRepository.save(token);
        return new TokenResponseDto(token);
    }
}
