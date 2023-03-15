package com.sourcery.clinicapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "")
    public ResponseEntity<Void> getUsers() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
