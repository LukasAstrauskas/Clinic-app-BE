package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.patient.model.PatientAppointmentsDto;
import com.sourcery.clinicapp.patient.model.TimeslotForPatient;
import com.sourcery.clinicapp.patient.model.PatientAppointmentsPage;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import com.sourcery.clinicapp.user.repository.UserRepository;
import com.sourcery.clinicapp.utils.FullNameCapitalisation;
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

    private final FullNameCapitalisation fullNameCapitalisation;

    private final PasswordEncoder encoder;

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
        User newUser = fullNameCapitalisation.capitalize(user);

        User finalUser = newUser.toBuilder()
                .id(UUID.randomUUID())
                .password(encoder.encode(user.getPassword()))
                .type("patient").build();

        userRepository.save(finalUser);
        return new ResponseEntity<>(finalUser.toString(), HttpStatus.CREATED);
    }

    public ResponseEntity<String> createAdmin(User user) {
        User newUser = fullNameCapitalisation.capitalize(user);

        User finalUser = newUser.toBuilder()
                .id(UUID.randomUUID())
                .password(encoder.encode(user.getPassword()))
                .type("admin").build();

        userRepository.save(finalUser);
        return new ResponseEntity<>(finalUser.toString(), HttpStatus.CREATED);
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

    public ResponseEntity<String> deletePatientById(UUID uuid) {
        try {
            userRepository.deletePatientById(uuid);
            return new ResponseEntity<>("The user with all appointments was deleted successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAdminById(UUID uuid) {
        userRepository.deleteAdminById(uuid);
        return new ResponseEntity<>("Succes", HttpStatus.OK);
    }

    public UserDTO getAUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<UserDTO> handlePatientSearch(String search) {
        String formatedSearch = search.toLowerCase();
        return userRepository.getPatientSearch(formatedSearch);
    }

    public List<UserDTO> handleAdminSearch(String search) {
        String formatedSearch = search.toLowerCase();
        return userRepository.getAdminSearch(formatedSearch);
    }

    public List<Physician> handlePhysicianSearch(Optional<String> search, Optional<String> occupation) {
        String formattedSearch = search.map(String::toLowerCase).orElse("");
        String formattedOccupation = occupation.map(String::toLowerCase).orElse("");
        return userRepository.getPhysicianSearch(formattedSearch, formattedOccupation);
    }


    public ResponseEntity<String> updateUserById(UUID uuid, User user) {
        User newUser = fullNameCapitalisation.capitalize(user);

        try {
            userRepository.updateUserById(newUser, uuid);
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
        PhysicianDto newUser = fullNameCapitalisation.capitalize(user);
        try {
            String encodedPassword = encoder.encode(user.getPassword());
            newUser.setPassword(encodedPassword);
            userRepository.updatePhysicianDtoUserById(newUser, id);
            if (user.getPassword().length() != 0) {
                userRepository.updatePassword(user.getPassword(), id);
            }
            return new ResponseEntity<>("The user, with id " + id + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The user with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }
}




