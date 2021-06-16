package com.example.springsecurity.jwt.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "auth_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.USER;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Token> tokens = new HashSet<>();
}
