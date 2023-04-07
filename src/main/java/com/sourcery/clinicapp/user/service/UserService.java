package com.sourcery.clinicapp.user.service;

import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.repository.AdditionalPhysicianInfoRepository;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

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
        return userRepository.getPhysicians();
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
}
