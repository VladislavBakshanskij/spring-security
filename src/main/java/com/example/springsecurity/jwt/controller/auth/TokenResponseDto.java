package com.example.springsecurity.jwt.controller.auth;

import com.example.springsecurity.jwt.model.Token;
import lombok.Value;

@Value
public class TokenResponseDto {
    String token;

    public TokenResponseDto(final Token token) {
        this.token = token.getToken();
    }
}
