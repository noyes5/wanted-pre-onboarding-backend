package com.wanted.onboarding.service;

import com.wanted.onboarding.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

public interface UsersService {
    void registerUser(Users users);
    String login(String email, String password);

}
