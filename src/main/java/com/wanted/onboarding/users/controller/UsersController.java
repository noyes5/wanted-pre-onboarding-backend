package com.wanted.onboarding.users.controller;

import com.wanted.onboarding.security.JwtTokenProvider;
import com.wanted.onboarding.users.dto.LoginDTO;
import com.wanted.onboarding.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/users/register")
    public ResponseEntity<String> register(@RequestBody LoginDTO loginDTO) {
        // 사용자 등록을 위한 로직 수행
        boolean isRegistered = usersService.registerUser(loginDTO.getEmail(), loginDTO.getPassword());

        if (isRegistered) {
            return new ResponseEntity<>("회원 가입 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 등록된 이메일입니다.", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        boolean isAuthenticated = usersService.authenticateUser(loginDTO.getEmail(), loginDTO.getPassword());
        System.out.println(loginDTO.getPassword());
        if (isAuthenticated) {
            String token = jwtTokenProvider.createToken(loginDTO.getEmail());
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패. 이메일과 비밀번호를 확인하세요.", HttpStatus.UNAUTHORIZED);
        }
    }
}
