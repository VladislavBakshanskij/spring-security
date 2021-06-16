package com.example.springsecurity.jwt.service.token;

import com.example.springsecurity.jwt.controller.auth.TokenResponseDto;

public interface TokenService {
    TokenResponseDto save(String username);
}
