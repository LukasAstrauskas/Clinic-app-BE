package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.repository.PhysicianInfoRepository;
import com.sourcery.clinicapp.physicianInfo.service.PhysicianInfoService;
import com.sourcery.clinicapp.user.model.*;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.utils.UserFieldHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserFieldHelper userFieldHelper;

    private final PasswordEncoder encoder;

    private final PhysicianInfoRepository physicianInfoRepository;

    private final PhysicianInfoService physicianInfoService;

    public int getPatientCount() {
        return userMapper.getPatientCount();
    }

    public int getPhysicianCount() {
        return userMapper.getPhysicianCount();
    }

    public int getAdmninCount() {
        return userMapper.getAdminCount();
    }

    public Short getPatientsByPhysicianIdAmount(UUID uuid) {
        return userMapper.getPatientsByPhysicianIdAmount(uuid);
    }

    public List<UserDTO> getPatientsLimited(Number offset) {
        return userMapper.getLimitedPatients(offset);
    }

    public List<UserDTO> getAdminsLimited(Number offset) {
        return userMapper.getLimitedAdmins(offset);
    }

    public List<UserDTO> getPatients() {
        return userMapper.getPatients();
    }

    public List<UserDTO> getPatientsWithAppointments(UUID uuid, int offset) {
        return userMapper.getPatientsByPhysicianId(uuid, offset);
    }

    public List<UserDTO> getAdmins() {
        return userMapper.getAdmins();
    }

    public ResponseEntity<String> deleteUserById(UUID uuid) {
        UserDTO userById = getUserById(uuid);
        if (userById.getType().equals(Type.PHYSICIAN.type())) {
            physicianInfoService.deletePhysicianInfo(userById.getId());
        }
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
        if (updateUser.getInfoID() != null && updateUser.getType().equals(Type.PHYSICIAN.type())) {
            physicianInfoService.updatePhysicianById(updateUser.getInfoID(), uuid);
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
        if (newUser.getInfoID() != null && newUser.getType().equals(Type.PHYSICIAN.type())) {
            physicianInfoService.insertInfo(userToSave.getId(), newUser.getInfoID());
        }
        return new ResponseEntity<>(saved ? "User saved." : "Some error.", HttpStatus.OK);
    }

    public LoggedUser getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoggedUser loggedUser = userMapper.getLoggedUser(auth.getName());
        loggedUser.setInitials(
                loggedUser.getName().substring(0, 1).concat(
                        loggedUser.getSurname().substring(0, 1)
                )
        );
        return loggedUser;
    }
}




