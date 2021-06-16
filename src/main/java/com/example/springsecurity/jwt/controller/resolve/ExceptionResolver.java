package com.example.springsecurity.jwt.controller.resolve;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> onUserNotFoundException(final UsernameNotFoundException e) {
        return ResponseEntity.of(Optional.of(e));
    }
}
