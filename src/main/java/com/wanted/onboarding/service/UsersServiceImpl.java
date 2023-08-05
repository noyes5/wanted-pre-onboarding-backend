package com.wanted.onboarding.service;

import com.wanted.onboarding.entity.Users;
import com.wanted.onboarding.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public void userRegister(Users users) {
        usersRepository.save(users);
    }
}
