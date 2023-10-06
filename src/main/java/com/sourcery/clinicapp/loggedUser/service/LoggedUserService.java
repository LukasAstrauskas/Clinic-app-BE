package com.sourcery.clinicapp.loggedUser.service;

import com.sourcery.clinicapp.loggedUser.mapper.LoggedUserMapper;
import com.sourcery.clinicapp.loggedUser.model.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoggedUserService {
    @Autowired
    private LoggedUserMapper loggedUserMapper;

    public LoggedUser getLoggedUser() {
        LoggedUser loggedUser = loggedUserMapper.getLoggedUser(getEmail());
        loggedUser.setInitials(
                loggedUser.getName().substring(0, 1).concat(
                        loggedUser.getSurname().substring(0, 1)
                )
        );
        return loggedUser;
    }

    public UUID getId() {
        return loggedUserMapper.getId(getEmail());
    }

    public String getEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
