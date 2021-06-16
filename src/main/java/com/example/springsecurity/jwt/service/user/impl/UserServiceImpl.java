package com.example.springsecurity.jwt.service.user.impl;

import com.example.springsecurity.jwt.model.User;
import com.example.springsecurity.jwt.repository.UserRepository;
import com.example.springsecurity.jwt.security.JwtTokenProvider;
import com.example.springsecurity.jwt.service.user.UserCreateDto;
import com.example.springsecurity.jwt.service.user.UserDto;
import com.example.springsecurity.jwt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider provider;

    @Override
    public User getByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
    }
}
