package com.sourcery.clinicapp.login.service;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public LoginDto checkLog(Login user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }
        return userRepository.checkLogIn(user).orElseThrow(() -> new NoSuchElementException("Invalid email or password"));
    }

}
