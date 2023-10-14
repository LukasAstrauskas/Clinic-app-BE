package com.sourcery.clinicapp.user.controller;

import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import com.sourcery.clinicapp.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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

    @GetMapping("search")
    public Collection<UserDTO> userSearch(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) String occupationId,
            @RequestParam(required = false) String type) {
        return userService.userSearch(search, occupationId, type);
    }

    //    CRUD OPERATIONS
    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @PostMapping("admin")
    public ResponseEntity<String> insertUser(@RequestBody User newUser) {
        return userService.insertUser(newUser);
    }

    @DeleteMapping("admin/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable("uuid") String uuid) {
        return userService.deleteUserById(uuid);
    }

    @PutMapping("admin")
    public ResponseEntity<String> updateUser(@RequestBody User userToUpdate) {
        System.out.println(userToUpdate.toString());
        log.debug("User with id: " + userToUpdate.getId() + " successfully updated");
        return userService.updateUser(userToUpdate);
    }
}
