package com.sourcery.clinicapp.security.service;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public AuthenticationService() {
    }

    public LoginDto authenticateUser(Login login) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return LoginDto.builder()
                .loggedUser(userService.getLoggedUser(authentication.getName()))
                .token(jwtService.generateToken(authentication))
                .build();
    }
}
