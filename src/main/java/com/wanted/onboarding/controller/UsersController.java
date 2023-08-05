package com.wanted.onboarding.controller;

import com.wanted.onboarding.entity.Users;
import com.wanted.onboarding.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    @Autowired
    UsersService service;

    @GetMapping("/users/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/users/register")
    public String register(Users users) {
        service.registerUser(users);

        return "redirect:/";
    }

}
