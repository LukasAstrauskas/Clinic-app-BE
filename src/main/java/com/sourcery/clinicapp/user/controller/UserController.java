package com.sourcery.clinicapp.user.controller;

import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.patient.model.PatientAppointmentsDto;
import com.sourcery.clinicapp.patient.model.PatientAppointmentsPage;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import com.sourcery.clinicapp.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    @GetMapping("patients")
    public List<UserDTO> getPatients() {
        return userService.getPatients();
    }

    @GetMapping("patientUpcomingAppointments/{id}")
    public List<PatientAppointmentsDto> getUpcomingAppointments(@PathVariable("id") UUID id){
        return  userService.getUpcomingPatientAppointments(id);
    }

    @GetMapping(value = "patients/{physicianId}/{offset}")
    public List<UserDTO> getPatientsByPhysicianId(@PathVariable UUID physicianId, @PathVariable Number offset) {
        return userService.getPatientsWithAppointments(physicianId, offset);
    }

    @GetMapping("patientPastAppointments/{id}/{offset}")
    public PatientAppointmentsPage<PatientAppointmentsDto> getPastAppointments(@PathVariable("id") UUID id, @PathVariable("offset") Number offset){
        return  userService.getMorePastPatientAppointments(id, offset);
    }

    @GetMapping(value = "patientSize")
    public Long getAmountOfPatients(){
        return userService.getAmountOfPatients();
    }

    @GetMapping(value = "patientsByPhysicianIdSize/{uuid}")
    public Short getPatientsByPhysicianIdAmount(@PathVariable("uuid") UUID uuid ) {return userService.getPatientsByPhysicianIdAmount(uuid);}

    @GetMapping(value = "physicianSize")
    public Long getAmountOfPhysicians(){
        return userService.getAmountOfPhysician();
    }


    @GetMapping(value = "adminSize")
    public Long getAmountOfAdmins(){
        return userService.getAmountOfAdmins();
    }


    @GetMapping(value ="patients/offset/{offset}" )
    public List<UserDTO> getLimitedPatients(@PathVariable("offset") Number offset){
        return userService.getPatientsLimited(offset);
    }

    @GetMapping(value = "admins/offset/{offset}")
    public List<UserDTO> getLimitedAdmins(@PathVariable("offset") Number offset){
        return userService.getAdminsLimited(offset);
    }

    @GetMapping("physicians")
    public List<UserDTO> getPhysician() {
        return userService.getPhysicians();
    }

    @GetMapping("admins")
    public List<UserDTO> getAdmins() {
        return userService.getAdmins();
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("admins")
    public ResponseEntity<String> createAdmin(@RequestBody User user ){
        return userService.createAdmin(user);
    }


    @PostMapping("patients")
    public ResponseEntity<String> createPatient(@RequestBody User user) {
        return userService.createPatient(user);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<String> removeUser(@PathVariable("uuid") UUID uuid) {
        return userService.deletePatientById(uuid);
    }

    @DeleteMapping(value = "admins/{uuid}")
    public ResponseEntity<String> removeAdmin(@PathVariable("uuid") UUID uuid) {
        return userService.deleteAdminById(uuid);
    }


    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") UUID id){
        return userService.getAUserById(id);
    }


    @GetMapping(value = "patientSearch/{search}")
    public List<UserDTO> handlePatientSearch(@PathVariable("search") String search){
     return userService.handlePatientSearch(search);
    }


    @GetMapping(value = "physicianSearch/")
    public List<Physician> handlePhysicianSearch(@RequestParam(name = "search", required = false) Optional<String> search,
                                                 @RequestParam(name = "occupation", required = false) Optional<String> occupation) {
        return userService.handlePhysicianSearch(search, occupation);
    }

    @GetMapping(value = "adminSearch/{search}")
    public List<UserDTO> handleAdminSearch(@PathVariable("search") String search){
        return  userService.handleAdminSearch(search);
    }



    @PutMapping ("{uuid}")
    public ResponseEntity<String> updateUserById(@RequestBody User user, @PathVariable("uuid") UUID uuid) {
        log.debug("User with id: " + uuid + " successfully updated");
        return userService.updateUserById(uuid, user);
    }
}
