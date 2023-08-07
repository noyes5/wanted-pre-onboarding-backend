package com.wanted.onboarding.users.service;

import com.wanted.onboarding.users.entity.Users;

public interface UsersService {
    void registerUser(Users users);
    boolean authenticateUser(String email, String password);
    int getUserIdByEmail(String email);
}
