package com.sourcery.clinicapp.login.service;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public UUID checkLog(Login user) {
        return userRepository.CheckLogIn(user);
    }

}
