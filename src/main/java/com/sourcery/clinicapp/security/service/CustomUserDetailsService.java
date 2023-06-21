package com.sourcery.clinicapp.security.service;

import com.sourcery.clinicapp.security.model.SecurityUser;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userMapper
                .findByEmail(userEmail)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userEmail + " not found."));
    }
}
