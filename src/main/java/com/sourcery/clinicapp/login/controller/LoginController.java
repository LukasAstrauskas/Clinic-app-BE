package com.sourcery.clinicapp.login.controller;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.login.service.LoginService;
import com.sourcery.clinicapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    @PostMapping("login")
    public LoginDto  checkLogIn(@RequestBody Login user) {

        UUID uuid = loginService.checkLog(user);
        String role = userService.getRoleById(uuid);

        return new LoginDto(uuid, role);
    }
}
