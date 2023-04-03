package com.sourcery.clinicapp.login.service;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public LoginDto checkLog(Login user) {

        UUID uuid = userRepository.checkLogIn(user);
        String role = userRepository.getRoleById(uuid);

        return new LoginDto(uuid, role);
    }

}
