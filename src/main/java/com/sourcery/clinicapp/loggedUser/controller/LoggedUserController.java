package com.sourcery.clinicapp.loggedUser.controller;

import com.sourcery.clinicapp.loggedUser.model.LoggedUser;
import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loggedUser")
public class LoggedUserController {

    @Autowired
    public LoggedUserService loggedUserService;

    @GetMapping()
    public LoggedUser getLoggedUser(){
        return loggedUserService.getLoggedUser();
    }
}
