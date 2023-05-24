package com.sourcery.clinicapp.login.controller;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public LoginDto checkLogIn(@RequestBody Login login) {
        return authenticationService.authenticateUser(login);
    }
}
