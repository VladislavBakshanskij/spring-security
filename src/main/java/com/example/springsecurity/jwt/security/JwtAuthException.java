package com.example.springsecurity.jwt.security;

public class JwtAuthException extends RuntimeException {
    public JwtAuthException(String token, Throwable cause) {
        super("Token is invalid: " + token, cause);
    }

    public JwtAuthException(String message) {
        super(message);
    }
}
