package com.wanted.onboarding.users.controller;

import com.wanted.onboarding.security.JwtTokenProvider;
import com.wanted.onboarding.users.entity.Users;
import com.wanted.onboarding.users.dto.LoginRequest;
import com.wanted.onboarding.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = usersService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        System.out.println(loginRequest.getPassword());
        if (isAuthenticated) {
            String token = jwtTokenProvider.createToken(loginRequest.getEmail());
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패. 이메일과 비밀번호를 확인하세요.", HttpStatus.UNAUTHORIZED);
        }
    }
}
