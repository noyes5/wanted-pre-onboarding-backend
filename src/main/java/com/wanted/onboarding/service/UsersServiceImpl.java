package com.wanted.onboarding.service;

import com.wanted.onboarding.entity.Users;
import com.wanted.onboarding.exception.DuplicateEmailException;
import com.wanted.onboarding.exception.InvalidEmailException;
import com.wanted.onboarding.exception.InvalidPasswordException;
import com.wanted.onboarding.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(Users users) throws RuntimeException {
        String email = users.getEmail();
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("유효하지 않은 이메일 형식입니다.");
        }

        Users existingUser = usersRepository.findByEmail(users.getEmail());
        if (existingUser != null) {
            throw new DuplicateEmailException("중복된 이메일이 이미 존재합니다.");
        }

        String rawPassword = users.getPassword();
        if (rawPassword.length() < 8) {
            throw new InvalidPasswordException("비밀번호는 8자리 이상 입력해주세요.");
        }
        users.setPassword(passwordEncoder.encode(rawPassword));

        usersRepository.save(users);
    }

    private boolean isValidEmail(String email) {
        String[] splitEmail = email.split("@");
        if (splitEmail[0].equals("")) {
            return false;
        }
        return splitEmail.length == 2;
    }

}
