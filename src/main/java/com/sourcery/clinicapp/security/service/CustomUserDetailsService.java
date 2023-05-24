package com.sourcery.clinicapp.security.service;

import com.sourcery.clinicapp.security.model.SecurityUser;
import com.sourcery.clinicapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(userEmail)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userEmail + " not found."));
    }
}
