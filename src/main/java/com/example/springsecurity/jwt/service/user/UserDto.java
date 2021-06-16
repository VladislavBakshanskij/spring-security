package com.example.springsecurity.jwt.service.user;

import com.example.springsecurity.jwt.model.Role;
import com.example.springsecurity.jwt.model.User;
import lombok.Value;

@Value
public class UserDto {
    long id;
    String username;
    Role role;
    String token;

    public UserDto(final User user, final String token) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.token = token;
    }
}
