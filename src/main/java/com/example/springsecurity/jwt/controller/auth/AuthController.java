package com.example.springsecurity.jwt.controller.auth;

import com.example.springsecurity.jwt.model.User;
import com.example.springsecurity.jwt.security.JwtTokenProvider;
import com.example.springsecurity.jwt.service.token.TokenService;
import com.example.springsecurity.jwt.service.user.UserCreateDto;
import com.example.springsecurity.jwt.service.user.UserDto;
import com.example.springsecurity.jwt.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtTokenProvider provider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public TokenResponseDto login(@Valid @RequestBody AuthDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        User user = userService.getByName(dto.getUsername());
        return tokenService.save(user.getUsername());
    }

    @PostMapping("refresh")
    public TokenResponseDto refresh(HttpServletRequest request) {
        String username = provider.getUsername(request);
        return tokenService.save(username);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok().build();
    }
}
