package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.model.PhysicianDto;
import com.sourcery.clinicapp.patientInfo.model.PatientAppointmentsDto;
import com.sourcery.clinicapp.patientInfo.model.TimeslotForPatient;
import com.sourcery.clinicapp.patientInfo.model.PatientAppointmentsPage;
import com.sourcery.clinicapp.physicianInfo.repository.PhysicianInfoRepository;
import com.sourcery.clinicapp.user.model.Type;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import com.sourcery.clinicapp.user.repository.UserRepository;
import com.sourcery.clinicapp.utils.UserFieldHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserFieldHelper userFieldHelper;

    private final PasswordEncoder encoder;

    private final PhysicianInfoRepository physicianInfoRepository;

    public Long getAmountOfPatients() {
        return userRepository.getAmountOfPatients();
    }

    public Short getPatientsByPhysicianIdAmount(UUID uuid) {
        return userRepository.getPatientsByPhysicianIdAmount(uuid);
    }

    public Long getAmountOfAdmins() {
        return userRepository.getAmountOfAdmins();
    }

    public Long getAmountOfPhysician() {
        return userRepository.getAmountOfPhysicians();
    }


    public List<UserDTO> getPatientsLimited(Number offset) {
        return userRepository.getLimitedPatients(offset);
    }

    public List<UserDTO> getAdminsLimited(Number offset) {
        return userRepository.getLimitedAdmins(offset);
    }


    public List<PatientAppointmentsDto> getUpcomingPatientAppointments(UUID id) {
        List<PatientAppointmentsDto> sortedAppointments = userRepository.getUpcomingPatientAppointments(id).stream()
                .sorted(Comparator.comparing(PatientAppointmentsDto::getTimeslot, Comparator.comparing(TimeslotForPatient::getDate)))
                .collect(Collectors.toList());
        return sortedAppointments;
    }


    public PatientAppointmentsPage<PatientAppointmentsDto> getMorePastPatientAppointments(UUID id, Number offset) {
        List<PatientAppointmentsDto> data = userRepository.getMorePastPatientAppointments(id, offset);
        int size = userRepository.getPastAppointmentAmount(id);
        PatientAppointmentsPage<PatientAppointmentsDto> patientAppointmentsPage = new PatientAppointmentsPage<>();
        patientAppointmentsPage.setData(data);
        patientAppointmentsPage.setTotal(size);
        return patientAppointmentsPage;
    }


    public ResponseEntity<String> createPatient(User user) {
        String capitalizedFullName = userFieldHelper.capitalizeFullName(user.getName());
        User userToSava = User.builder()
                .id(UUID.randomUUID())
                .name(capitalizedFullName)
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .type("patient")
                .build();
        userRepository.saveUser(userToSava);
        return new ResponseEntity<>(userToSava.toString(), HttpStatus.CREATED);
    }

    public ResponseEntity<String> createAdmin(User user) {
        String capitalizedFullName = userFieldHelper.capitalizeFullName(user.getName());
        User userToSava = User.builder()
                .id(UUID.randomUUID())
                .name(capitalizedFullName)
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .type("admin")
                .build();
        userRepository.saveUser(userToSava);
        return new ResponseEntity<>(userToSava.toString(), HttpStatus.CREATED);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.getUsers();
    }

    public List<UserDTO> getPatients() {
        return userRepository.getPatients();
    }

    public List<UserDTO> getPatientsWithAppointments(UUID uuid, Number offset) {
        return userRepository.getPatientsByPhysicianId(uuid, offset);
    }

    public List<UserDTO> getPhysicians() {
        return userRepository.getPhysiciansType();
    }


    public List<UserDTO> getAdmins() {
        return userRepository.getAdmins();
    }

    public ResponseEntity<String> deleteUserById(UUID uuid) {
        UserDTO userById = getUserById(uuid);
        if (userById.getType().equals(Type.PHYSICIAN.type())) {
            physicianInfoRepository.deletePhysicianInfo(userById.getId());
        }
        if (userRepository.deleteUserById(userById.getId())) {
            return new ResponseEntity<>("The user was deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The user with the provided ID not found: " + userById.getId(), HttpStatus.NOT_FOUND);
        }
    }

    public UserDTO getUserById(UUID id) {
        return userRepository.getUserById(id).orElseThrow(() -> new NoSuchElementException("No user with id: " + id));
    }

    public List<UserDTO> handlePatientSearch(String search) {
        String formattedSearch = search.toLowerCase();
        return userRepository.getPatientSearch(formattedSearch);
    }

    public List<UserDTO> handleAdminSearch(String search) {
        String formattedSearch = search.toLowerCase();
        return userRepository.getAdminSearch(formattedSearch);
    }

    public List<Physician> handlePhysicianSearch(String search, String occupation) {
        String formattedSearch = search.toLowerCase();
        String formattedOccupation = occupation.toLowerCase();
        return userRepository.getPhysicianSearch(formattedSearch, formattedOccupation);
    }


    public ResponseEntity<String> updateUserById(UUID uuid, User user) {
        String capitalizedFullName = userFieldHelper.capitalizeFullName(user.getName());
        user.setName(capitalizedFullName);
        try {
            userRepository.updateUserById(user, uuid);
            if (user.getPassword().length() != 0) {
                String encodedPassword = encoder.encode(user.getPassword());
                userRepository.updatePassword(encodedPassword, uuid);
            }
            return new ResponseEntity<>("The user, with id " + uuid + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updatePhysicianDtoUserById(PhysicianDto user, UUID id) {
        String capitalizedFullName = userFieldHelper.capitalizeFullName(user.getName());
        user.setName(capitalizedFullName);
        try {
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.updatePhysicianDtoUserById(user, id);
            if (user.getPassword().length() != 0) {
                userRepository.updatePassword(user.getPassword(), id);
            }
            return new ResponseEntity<>("The user, with id " + id + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }
}




