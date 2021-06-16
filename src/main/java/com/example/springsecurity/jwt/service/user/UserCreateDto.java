package com.example.springsecurity.jwt.service.user;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class UserCreateDto {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
