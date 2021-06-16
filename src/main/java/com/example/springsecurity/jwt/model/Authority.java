package com.example.springsecurity.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority { // permissions
    READ("read"),
    WRITE("write");

    private final String name;
}
