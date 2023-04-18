package com.sourcery.clinicapp.user.controller;

import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.occupation.repository.OccupationRepository;
import com.sourcery.clinicapp.user.repository.UserRepository;
import com.sourcery.clinicapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    public UserRepository test;
    public OccupationRepository occupationRepository;

    @GetMapping(value = "patients")
    public List<User> getPatients() {
        return userService.getPatients();
    }



    @GetMapping(value = "patientSize")
    public Long getAmountOfPatients(){
        return userService.getAmountOfPatients();
    }

    @GetMapping(value = "physicianSize")
    public Long getAmountOfPhysicians(){
        return userService.getAmountOfPhysician();
    }





    @GetMapping(value = "adminSize")
    public Long getAmountOfAdmins(){
        return userService.getAmountOfAdmins();
    }


    @GetMapping(value ="patients/limit/{limit}" )
    public List<User> getLimitedPatients(@PathVariable("limit") Number limit){
        return userService.getPatientsLimited(limit);
    }

    @GetMapping(value = "admins/limit/{limit}")
    public List<User> getLimitedAdmins(@PathVariable("limit") Number limit){
        return userService.getAdminsLimited(limit);
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
    public ResponseEntity<String> createAdmin(@RequestBody User user) {
        return userService.createAdmin(user);
    }

    @PostMapping(value = "patients")
    public ResponseEntity<String> createPatient(@RequestBody User user) {
        return userService.createPatient(user);
    }

    @DeleteMapping(value = "patients/{uuid}")
    public ResponseEntity<String> removePatient(@PathVariable("uuid") UUID uuid) {
        return userService.deletePatientById(uuid);
    }

    @DeleteMapping(value = "admins/{uuid}")
    public ResponseEntity<String> removeAdmin(@PathVariable("uuid") UUID uuid) {
        return userService.deleteAdminById(uuid);
    }


    @GetMapping(value = "{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.getAUserById(id);
    }


    @GetMapping(value = "test/{search}")
    public List<User> handlePatientSearch(@PathVariable("search") String search){
     return  test.getPatientSearch(search);
    }

    @GetMapping(value = "physicianSearch/{search}")
    public List<Physician> handlePhysicianSearch(@PathVariable("search") String search){
        return  test.getPhysicianSearch(search);
    }

    @GetMapping(value = "adminSearch/{search}")
    public List<User> handleAdminSearch(@PathVariable("search") String search){
        return  test.getAdminSearch(search);
    }


}
