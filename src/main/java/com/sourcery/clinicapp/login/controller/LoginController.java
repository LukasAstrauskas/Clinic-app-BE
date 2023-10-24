package com.sourcery.clinicapp.login.controller;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    @GetMapping("public")
    public String publicInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Is authenticated: " + auth.isAuthenticated());
        String eml = "No email.";
        if (auth.getName() != null) {
            eml = auth.getName();
        }
        String auths = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        return String.format("User email: %s. Auths: %s.", eml, auths);
    }
}
