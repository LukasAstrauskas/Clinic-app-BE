package com.sourcery.clinicapp.controller;

import com.sourcery.clinicapp.dto.PhysicianDto;
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
    public List<User> getPhysicians(){
        return userService.getPhysicians();
    }

    @PostMapping(value = "patient")
    public void addPatients(@RequestBody User user)
    {
        userService.addPatient(user);
    }


    @DeleteMapping(path = "{uuid}")
    public void removeUser(@PathVariable("uuid")UUID uuid)
    {
        userService.removeUser(uuid);
    }

    @GetMapping("physicianInfo")
    public List<Physician>getAllPhysicians(){
        return userService.getAllPhysiciansWithAdditionalInfo();
    }

//    @GetMapping(path = "#{id}")
//    public Physician getPhysicianById(@PathVariable("id") UUID id){
//       return   userService.getPhysicianById(id);
//    }


//    @PostMapping("test")
//    public Physician createPhysician(@RequestBody PhysicianDto physician) {
//        return userService.createPhysician(physician);
//    }


    @PostMapping("physicianInfo")
    public void createPhysician(@RequestBody PhysicianDto physician) {
        userService.createPhysician(physician);
    }


    @GetMapping("occupations")
    public List<Occupation> GetAllOccupations()
    {
    return occupationRepository.getAllOccupations();
    }


    @GetMapping(path = "{id}")
    public Physician testing (@PathVariable("id") UUID id){
        return userService.getPhysicianById(id);
    }



//    @PostMapping("physician")
//    public Physician createPhysician(CreatePhysicianDto dto) {
//
//    }

    /*
    {
        id: ""
        name: "",
        email: "",
        occupation: "Petras"
    }

    * */

}
