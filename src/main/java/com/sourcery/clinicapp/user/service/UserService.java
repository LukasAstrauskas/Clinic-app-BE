package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.PatientAppointmentsDto;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotForPatient;
import com.sourcery.clinicapp.user.model.Page;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


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
        return userRepository.GetLimitedPatients(offset);
    }

    public List<User> getAdminsLimited(Number offset){
        return userRepository.GetLimitedAdmins(offset);
    }

    public List<PatientAppointmentsDto> getUpcomingPatientAppointments(UUID id){
        List<PatientAppointmentsDto> sortedAppointments = userRepository.getUpcomingPatientAppointments(id).stream()
                .sorted(Comparator.comparing(PatientAppointmentsDto::getTimeslot, Comparator.comparing(TimeslotForPatient::getDate).reversed()))
                .collect(Collectors.toList());
        return sortedAppointments;
    }


    public  Page /*List*/ <PatientAppointmentsDto> getMorePastPatientAppointments(UUID id, Number offset){
        var data =userRepository.getMorePastPatientAppointments(id, offset);
        var size = userRepository.getPastAppointmentAmount(id);
        Page<PatientAppointmentsDto> page = new Page<>();
        page.setData(data);
        page.setTotal(size);
        return page;
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

            return new ResponseEntity<>("The user was deleted successfully.", HttpStatus.OK);
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

    public List<Physician>handlePhysicianSearch(String search){
        String formatedSearch = search.toLowerCase();
        return userRepository.getPhysicianSearch(formatedSearch);
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




