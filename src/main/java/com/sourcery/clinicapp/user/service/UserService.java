package com.sourcery.clinicapp.user.service;


import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.model.PhysicianDto;
import com.sourcery.clinicapp.patientInfo.model.PatientAppointmentsDto;
import com.sourcery.clinicapp.patientInfo.model.TimeslotForPatient;
import com.sourcery.clinicapp.patientInfo.model.PatientAppointmentsPage;
import com.sourcery.clinicapp.physicianInfo.repository.PhysicianInfoRepository;
import com.sourcery.clinicapp.physicianInfo.service.PhysicianInfoService;
import com.sourcery.clinicapp.user.model.CreateUserDTO;
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

    private final PhysicianInfoService physicianInfoService;

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

    public List<UserDTO> getPatients() {
        return userRepository.getPatients();
    }

    public List<UserDTO> getPatientsWithAppointments(UUID uuid, Number offset) {
        return userRepository.getPatientsByPhysicianId(uuid, offset);
    }

    public List<UserDTO> getAdmins() {
        return userRepository.getAdmins();
    }

    public ResponseEntity<String> deleteUserById(UUID uuid) {
        UserDTO userById = getUserById(uuid);
        if (userById.getType().equals(Type.PHYSICIAN.type())) {
            physicianInfoService.deletePhysicianInfo(userById.getId());
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


    public ResponseEntity<String> updateUserById(UUID uuid, CreateUserDTO updateUser) {
        String name = userFieldHelper.capitalizeFirstLetter(updateUser.getName());
        String surname = userFieldHelper.capitalizeFirstLetter(updateUser.getSurname());
        String fullName = name.concat(" ").concat(surname);

        User userToUpdate = User.builder()
                .name(fullName)
                .email(updateUser.getEmail())
                .build();

        userRepository.updateUserById(userToUpdate, uuid);
        if (updateUser.getPassword().length() != 0) {
            String encodedPassword = encoder.encode(updateUser.getPassword());
            userRepository.updatePassword(encodedPassword, uuid);
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
        boolean saved = userRepository.saveUser(userToSave);
        if (newUser.getInfoID() != null && newUser.getType().equals(Type.PHYSICIAN.type())) {
            physicianInfoService.insertInfo(userToSave.getId(), newUser.getInfoID());
        }
        return new ResponseEntity<>(saved ? "User saved." : "Some error.", HttpStatus.OK);
    }
}




