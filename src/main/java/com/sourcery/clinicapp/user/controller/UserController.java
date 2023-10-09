package com.sourcery.clinicapp.user.controller;

import com.sourcery.clinicapp.user.model.Physician;
import com.sourcery.clinicapp.user.model.CreateUserDTO;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import com.sourcery.clinicapp.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;


    @GetMapping("userCount")
    public int getUserCount(@RequestParam(required = false) String userType) {
        return userService.getUserCount(userType);
    }

    @GetMapping
    public Collection<UserDTO> getUsers(@RequestParam(required = false, defaultValue = "0") int offset, @RequestParam String userType) {
        return userService.getUsers(offset, userType);
    }

    //    only for physician
    @GetMapping("physicianPatients")
    public List<UserDTO> getPhysicianPatients(@RequestParam(required = false, defaultValue = "0") int offset) {
        return userService.getPhysicianPatients(offset);
    }
    //    only for physician
    @GetMapping("amountOfPhysicianPatients")
    public int amountOfPhysicianPatients() {
        return userService.amountOfPhysicianPatients();
    }

    @GetMapping("patientSearch/{search}")
    public List<UserDTO> handlePatientSearch(@PathVariable("search") String search) {
        return userService.handlePatientSearch(search);
    }

    @GetMapping("physicianSearch/")
    public List<Physician> handlePhysicianSearch(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "occupation", required = false, defaultValue = "") String occupation) {
        return userService.handlePhysicianSearch(search, occupation);
    }

    @GetMapping("adminSearch/{search}")
    public List<UserDTO> handleAdminSearch(@PathVariable("search") String search) {
        return userService.handleAdminSearch(search);
    }

    //    CRUD OPERATIONS
    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping("admin")
    public ResponseEntity<String> insertUser(@RequestBody CreateUserDTO newUser) {
        return userService.insertUser(newUser);
    }

    @DeleteMapping("admin/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable("uuid") UUID uuid) {
        return userService.deleteUserById(uuid);
    }

    @PutMapping("admin")
    public ResponseEntity<String> updateUser(@RequestBody User userToUpdate) {
        System.out.println(userToUpdate.toString());
        log.debug("User with id: " + userToUpdate.getId() + " successfully updated");
        return userService.updateUser(userToUpdate);
    }
}
