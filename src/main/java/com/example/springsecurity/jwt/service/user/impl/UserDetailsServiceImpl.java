package com.example.springsecurity.jwt.service.user.impl;

import com.example.springsecurity.jwt.model.User;
import com.example.springsecurity.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Класс, который унаследован от UserDetailsService -> сервис, который загружает пользователя Spring Security.
 * То есть пользователя с бд мы просто замаптим в пользователя Spring Security.
 *
 * @see org.springframework.security.core.userdetails.User
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getRole().getAuthorities());
    }
}
