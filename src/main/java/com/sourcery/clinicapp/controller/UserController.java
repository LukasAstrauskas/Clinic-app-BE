package com.sourcery.clinicapp.controller;

import com.sourcery.clinicapp.model.User;
import com.sourcery.clinicapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    @GetMapping(value = "patients")
    public List<User> getPatients(){
        return userService.getPatients();
    }

    @GetMapping(value = "physicians")
    public List<User> getPhysicians(){
        return userService.getPhysicians();
    }

    @PostMapping(value = "addpatients")
    public void addPatients(@RequestBody User user)
    {
        userService.addPatient(user);
    }

    @PostMapping(value = "addphysicians")
    public void addPhysicians(@RequestBody User user)
    {
        userService.addPhysician(user);
    }






}
