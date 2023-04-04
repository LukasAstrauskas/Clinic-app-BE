package com.sourcery.clinicapp.user.controller;

import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.occupation.repository.OccupationRepository;
import com.sourcery.clinicapp.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    public OccupationRepository occupationRepository;

    @GetMapping(value = "patients")
    public List<User> getPatients() {
        return userService.getPatients();
    }

    @GetMapping(value = "physicians")
    public List<User> getPhysician() {
        return userService.getPhysicians();
    }

    @GetMapping(value = "admins")
    public List<User> getAdmins() {
        return userService.getAdmins();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping(value = "admins")
    public ResponseEntity<String> createAdmin(@RequestBody User user ){
        return userService.createAdmin(user);
    }


    @PostMapping(value = "patients")
    public ResponseEntity<String> createPatient(@RequestBody User user) {
        return userService.createPatient(user);
    }

    @DeleteMapping(value = "{uuid}")
    public ResponseEntity<String> removeUser(@PathVariable("uuid") UUID uuid) {
        return userService.deleteUserById(uuid);
    }

    @GetMapping(value = "{id}")
    public User getUserById(@PathVariable("id") UUID id){
        return userService.getAUserById(id);
    }

    @PostMapping(value = "/update/{uuid}")
    public ResponseEntity<String> updateUserById(@RequestBody User user, @PathVariable("uuid") UUID uuid) {
        log.debug("User with id: " + uuid + " successfully updated");
        return userService.updateUserById(uuid, user);
    }
}
