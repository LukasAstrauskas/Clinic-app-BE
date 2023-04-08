package com.sourcery.clinicapp.login.controller;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.login.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping("login")
    public LoginDto  checkLogIn(@RequestBody Login user) {
        return loginService.checkLog(user);
    }
}
