package com.example.springsecurity.jwt.controller.auth;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Value
public class AuthDto {
    @NotBlank
    @Length(min = 3)
    String username;
    @NotBlank
    @Length(min = 3)
    String password;
}
