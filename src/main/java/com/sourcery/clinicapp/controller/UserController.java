package com.sourcery.clinicapp.controller;

import com.sourcery.clinicapp.dto.LoginDto;
import com.sourcery.clinicapp.dto.PhysicianDto;
import com.sourcery.clinicapp.model.Login;
import com.sourcery.clinicapp.model.Occupation;
import com.sourcery.clinicapp.model.Physician;
import com.sourcery.clinicapp.model.User;
import com.sourcery.clinicapp.repository.OccupationRepository;
import com.sourcery.clinicapp.repository.UserRepository;
import com.sourcery.clinicapp.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;
    public UserRepository userRepository;

    public OccupationRepository occupationRepository;

    @GetMapping(value = "patients")
    public List<User> getPatients(){
        return userService.getPatients();
    }

    @GetMapping(value = "physicians")
    public List<User> getPhysician(){
        return userService.getPhysicians();
    }

    @GetMapping(value = "admins")
    public List<User> getAdmins() {
        return userService.getAdmins();
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping(value = "patients")
    public void createPatient(@RequestBody User user)
    {
        userService.createPatient(user);
    }

    @PostMapping("login")
    public UUID checkLogIn(@RequestBody Login user){
        return userService.CheckLog(user);
    }


    @DeleteMapping(path = "{uuid}")
    public void removeUser(@PathVariable("uuid")UUID uuid)
    {
        userService.removeUser(uuid);
    }

    @GetMapping("physicianInfo")
    public List<Physician>getAllPhysiciansWithAdditionalInfo(){
        return userService.getAllPhysiciansWithAdditionalInfo();
    }

    @PostMapping("physicianInfo")
    public void createPhysician(@RequestBody PhysicianDto physician) {
        userService.createPhysician(physician);
    }


    @GetMapping("occupations")
    public List<Occupation> GetAllOccupations()
    {
    return occupationRepository.getAllOccupations();
    }


    @GetMapping(path = "physicians/{id}")
    public Physician GetPhysicianWithAdditionalInfo (@PathVariable("id") UUID id){
        return userService.getPhysicianById(id);
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") UUID id ){
        return userService.getUserById(id);
    }


}
