package com.wanted.onboarding.users.service;

import com.wanted.onboarding.users.entity.Users;
import com.wanted.onboarding.exception.DuplicateEmailException;
import com.wanted.onboarding.exception.InvalidEmailException;
import com.wanted.onboarding.exception.InvalidPasswordException;
import com.wanted.onboarding.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public boolean registerUser(String email, String password) throws RuntimeException {
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("유효하지 않은 이메일 형식입니다.");
        }

        Optional<Users> existingUser = usersRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateEmailException("중복된 이메일이 이미 존재합니다.");
        }
        if (password.length() < 8) {
            throw new InvalidPasswordException("비밀번호는 8자리 이상 입력해주세요.");
        }
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        usersRepository.save(user);

        return true;
    }
    private boolean isValidEmail(String email) {
        String[] splitEmail = email.split("@");
        if (splitEmail[0].equals("")) {
            return false;
        }
        return splitEmail.length == 2;
    }

    private Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
    @Override
    public boolean authenticateUser(String email, String password) {
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("유효하지 않은 이메일 형식입니다.");
        }
        if (password.length() < 8) {
            throw new InvalidPasswordException("비밀번호는 8자리 이상 입력해주세요.");
        }

        Optional<Users> optionalUser = getUserByEmail(email);
        boolean isPasswordValid = verifyPassword(password, optionalUser.get().getPassword());

        return optionalUser.isPresent() && isPasswordValid;
    }


    private boolean verifyPassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);
    }





}
