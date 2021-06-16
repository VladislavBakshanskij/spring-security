package com.example.springsecurity.jwt.service.user;

import com.example.springsecurity.jwt.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getByName(String username);
}
