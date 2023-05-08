package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final TimeslotService timeslotService;

    public Long getAmountOfPatients(){
       return userRepository.getAmountOfPatients();
    }
    public Long getAmountOfAdmins(){
        return userRepository.getAmountOfAdmins();
    }

    public Long getAmountOfPhysician(){
        return userRepository.getAmountOfPhysicians();
    }


    public List<User> getPatientsLimited(Number offset){
        return userRepository.getLimitedPatients(offset);
    }

    public List<User> getAdminsLimited(Number offset){
        return userRepository.getLimitedAdmins(offset);
    }


    public ResponseEntity<String> createPatient(User user) {
        User newUser = user.toBuilder().id(UUID.randomUUID()).type("patient").build();
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser.toString(), HttpStatus.CREATED);
    }

    public ResponseEntity<String> createAdmin(User user) {
        User newUser = user.toBuilder().id(UUID.randomUUID()).type("admin").build();
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser.toString(), HttpStatus.CREATED);
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    public List<User> getPatients() {
        return userRepository.getPatients();
    }

    public List<User> getPhysicians() {
        return userRepository.getPhysiciansType();
    }



    public List<User> getAdmins() {
        return userRepository.getAdmins();
    }

    public ResponseEntity<String> deletePatientById(UUID uuid) {
        try {
            userRepository.deletePatientById(uuid);
            timeslotService.deleteAllTimeslotsByPatientId(uuid);
            return new ResponseEntity<>("The user with all appointments was deleted successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAdminById(UUID uuid) {
        userRepository.deleteAdminById(uuid);
        return new ResponseEntity<>("Succes", HttpStatus.OK);
    }

    public User getAUserById(UUID id){
        return userRepository.findById(id);
    }

    public List<User>handlePatientSearch(String search){
        String formatedSearch = search.toLowerCase();
        return userRepository.getPatientSearch(formatedSearch);
    }
    public List<User>handleAdminSearch(String search){
        String formatedSearch = search.toLowerCase();
        return userRepository.getAdminSearch(formatedSearch);
    }

    public List<Physician>handlePhysicianSearch(Optional<String>  search, Optional<String> occupation){
        String formattedSearch = search.map(String::toLowerCase).orElse("");
        String formattedOccupation = occupation.map(String::toLowerCase).orElse("");
        return userRepository.getPhysicianSearch(formattedSearch, formattedOccupation);
    }


    public ResponseEntity<String> updateUserById(UUID uuid, User user) {
        try {
            userRepository.updateUserById(user, uuid);
            return new ResponseEntity<>("The user, with id " + uuid + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<String> updatePhysicianDtoUserById(PhysicianDto user, UUID id) {
        try {
            userRepository.updatePhysicianDtoUserById(user, id);
            return new ResponseEntity<>("The user, with id " + id + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }
}




