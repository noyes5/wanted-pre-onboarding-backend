package com.wanted.onboarding.controller;

import com.wanted.onboarding.entity.Users;
import com.wanted.onboarding.exception.InvalidLoginException;
import com.wanted.onboarding.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/users/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/users/register")
    public String register(Users users) {
        usersService.registerUser(users);

        return "login";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token = usersService.login(email, password);
        return ResponseEntity.ok(token);
    }
}
