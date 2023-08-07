package com.wanted.onboarding.users.service;

import com.wanted.onboarding.users.entity.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    boolean registerUser(String email, String password);
    boolean authenticateUser(String email, String password);
}
