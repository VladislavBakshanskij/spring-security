package com.example.springsecurity.jwt.repository;

import com.example.springsecurity.jwt.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
