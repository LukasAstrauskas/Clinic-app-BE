package com.sourcery.clinicapp.user.controller;

import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import com.sourcery.clinicapp.user.model.CreateUserDTO;
import com.sourcery.clinicapp.user.model.LoggedUser;
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

    @GetMapping("loggedUser")
    public LoggedUser getLoggedUser(){
        return userService.getLoggedUser();
    }

    @GetMapping("patients")
    public List<UserDTO> getPatients() {
        return userService.getPatients();
    }

    @GetMapping("patients/{physicianId}/{offset}")
    public List<UserDTO> getPatientsByPhysicianId(@PathVariable UUID physicianId, @PathVariable int offset) {
        return userService.getPatientsWithAppointments(physicianId, offset);
    }

    @GetMapping("patientCount")
    public int getPatientCount() {
        return userService.getPatientCount();
    }

    @GetMapping("physicianCount")
    public int getPhysicianCount() {
        return userService.getPhysicianCount();
    }

    @GetMapping("adminCount")
    public int getAdminCount() {
        return userService.getAdmninCount();
    }

    @GetMapping("patientsByPhysicianIdSize/{uuid}")
    public Short getPatientsByPhysicianIdAmount(@PathVariable("uuid") UUID uuid) {
        return userService.getPatientsByPhysicianIdAmount(uuid);
    }

    @GetMapping("patients/offset/{offset}")
    public List<UserDTO> getLimitedPatients(@PathVariable("offset") Number offset) {
        return userService.getPatientsLimited(offset);
    }

    @GetMapping("admins/offset/{offset}")
    public List<UserDTO> getLimitedAdmins(@PathVariable("offset") Number offset) {
        return userService.getAdminsLimited(offset);
    }

    @GetMapping("admins")
    public List<UserDTO> getAdmins() {
        return userService.getAdmins();
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
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO newUser) {
        return userService.createUser(newUser);
    }

    @DeleteMapping("admin/{uuid}")
    public ResponseEntity<String> deleteUser(@PathVariable("uuid") UUID uuid) {
        return userService.deleteUserById(uuid);
    }

    @PutMapping("admin/{uuid}")
    public ResponseEntity<String> updateUserById(@PathVariable("uuid") UUID uuid, @RequestBody CreateUserDTO createUserDTO) {
        log.debug("User with id: " + uuid + " successfully updated");
        return userService.updateUserById(uuid, createUserDTO);
    }
}
