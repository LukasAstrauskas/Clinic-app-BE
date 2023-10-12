package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import com.sourcery.clinicapp.patientInfo.service.PatientInfoService;
import com.sourcery.clinicapp.user.model.*;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.utils.UserFieldHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserFieldHelper userFieldHelper;

    private final PasswordEncoder encoder;

    private final LoggedUserService loggedUserService;

    private final PatientInfoService infoService;


    public int getUserCount(String userType) {
        return userMapper.getUserCount(userType);
    }

    public Collection<UserDTO> getUsers(int offset, String userType) {
        return userMapper.getUsers(offset, userType);
    }

    public int amountOfPhysicianPatients() {
        UUID physicianId = loggedUserService.getId();
        return userMapper.amountOfPhysicianPatients(physicianId);
    }


    public List<UserDTO> getPhysicianPatients(int offset) {
        UUID physicianId = loggedUserService.getId();
        return userMapper.getPhysicianPatients(physicianId, offset);
    }


    public ResponseEntity<String> deleteUserById(UUID uuid) {
        UserDTO userById = getUserById(uuid);

        if (userMapper.deleteUserById(userById.getId())) {
            return new ResponseEntity<>("The user was deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The user with the provided ID not found: " + userById.getId(), HttpStatus.NOT_FOUND);
        }
    }

    public UserDTO getUserById(UUID id) {
        return userMapper.getUserById(id).orElseThrow(() -> new NoSuchElementException("No user with id: " + id));
    }

    public Collection<UserDTO> userSearch(String search, UUID occupationId, String type) {
        return userMapper.userSearch(search.toLowerCase(), occupationId, type);
    }

    public ResponseEntity<String> updateUser(User userToUpdate) {
        userToUpdate.setName(userFieldHelper.capitalizeFirstLetter(userToUpdate.getName()));
        userToUpdate.setSurname(userFieldHelper.capitalizeFirstLetter(userToUpdate.getSurname()));

        userMapper.updateUser(userToUpdate);

        if (userToUpdate.getPassword().length() != 0) {
            String encodedPassword = encoder.encode(userToUpdate.getPassword());
            userMapper.updatePassword(encodedPassword, userToUpdate.getId());
        }

        return new ResponseEntity<>("The user, with id " + userToUpdate.getId() + " was updated successfully.", HttpStatus.OK);
    }

    public ResponseEntity<String> insertUser(User newUser) {
        String name = userFieldHelper.capitalizeFirstLetter(newUser.getName());
        String surname = userFieldHelper.capitalizeFirstLetter(newUser.getSurname());
        UUID userId = UUID.randomUUID();
        User userToSave = User.builder()
                .id(userId)
                .name(name)
                .surname(surname)
                .email(newUser.getEmail())
                .password(encoder.encode(newUser.getPassword()))
                .type(newUser.getType())
                .occupationId(newUser.getOccupationId())
                .build();
        boolean saved = userMapper.insertUser(userToSave);
        if (userToSave.getType().equals(Type.PATIENT.type())){
            PatientInfo patientInfo = PatientInfo.builder()
                    .userId(userId)
                    .country("Lituanica")
                    .gender("Nonbinary")
                    .city("Zion")
                    .build();
            infoService.createPatientInfo(patientInfo);
        }

        return new ResponseEntity<>(saved ? "User saved." : "Some error.", HttpStatus.OK);
    }
}
