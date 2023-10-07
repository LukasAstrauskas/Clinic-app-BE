package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import com.sourcery.clinicapp.user.model.Physician;
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


    public List<UserDTO> getPhysicianPatients( int offset) {
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

    public List<UserDTO> handlePatientSearch(String search) {
        String formattedSearch = search.toLowerCase();
        return userMapper.getPatientSearch(formattedSearch);
    }

    public List<UserDTO> handleAdminSearch(String search) {
        String formattedSearch = search.toLowerCase();
        return userMapper.getAdminSearch(formattedSearch);
    }

    public List<Physician> handlePhysicianSearch(String search, String occupation) {
        String formattedSearch = search.toLowerCase();
        String formattedOccupation = occupation.toLowerCase();
        return userMapper.getPhysicianSearch(formattedSearch, formattedOccupation);
    }


    public ResponseEntity<String> updateUserById(UUID uuid, CreateUserDTO updateUser) {
        String name = userFieldHelper.capitalizeFirstLetter(updateUser.getName());
        String surname = userFieldHelper.capitalizeFirstLetter(updateUser.getSurname());
        String fullName = name.concat(" ").concat(surname);

        User userToUpdate = User.builder()
                .name(fullName)
                .email(updateUser.getEmail())
                .build();

        userMapper.updateUserById(userToUpdate, uuid);
        if (updateUser.getPassword().length() != 0) {
            String encodedPassword = encoder.encode(updateUser.getPassword());
            userMapper.updatePassword(encodedPassword, uuid);
        }

        return new ResponseEntity<>("The user, with id " + uuid + " was updated successfully.", HttpStatus.OK);
    }

    public ResponseEntity<String> createUser(CreateUserDTO newUser) {
        String name = userFieldHelper.capitalizeFirstLetter(newUser.getName());
        String surname = userFieldHelper.capitalizeFirstLetter(newUser.getSurname());
        String fullName = name.concat(" ").concat(surname);
        User userToSave = User.builder()
                .id(UUID.randomUUID())
                .name(fullName)
                .email(newUser.getEmail())
                .password(encoder.encode(newUser.getPassword()))
                .type(newUser.getType())
                .build();
        boolean saved = userMapper.saveUser(userToSave);

        return new ResponseEntity<>(saved ? "User saved." : "Some error.", HttpStatus.OK);
    }
}




