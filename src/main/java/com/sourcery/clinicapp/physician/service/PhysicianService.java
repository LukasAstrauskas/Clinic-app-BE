package com.sourcery.clinicapp.physician.service;

import com.sourcery.clinicapp.physician.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.physician.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.repository.AdditionalPhysicianInfoRepository;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.repository.UserRepository;
import com.sourcery.clinicapp.user.service.UserService;
import com.sourcery.clinicapp.utils.FullNameCapitalisation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhysicianService {

    private final AdditionalPhysicianInfoRepository additionalPhysicianInfoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FullNameCapitalisation fullNameCapitalisation;
    private final PasswordEncoder encoder;

    public void createPhysician(PhysicianDto physicianDto) {
        PhysicianDto newUser = fullNameCapitalisation.capitalize(physicianDto);
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .password(encoder.encode(newUser.getPassword()))
                .type("physician")
                .build();
        userRepository.save(user);
        AdditionalPhysicianInfo info = AdditionalPhysicianInfo.builder()
                .userId(user.getId())
                .occupationId(newUser.getOccupationId())
                .build();
        additionalPhysicianInfoRepository.insertInfo(info);
    }

    public List<Physician> getPhysiciansWithAdditionalInfo() {
        return userRepository.getPhysicians();
    }

    public List<Physician> getLimitedPhysiciansWithAdditionalInfo(Number offset) {
        return userRepository.getLimitedPhysicians(offset);
    }

    public Physician getPhysicianById(UUID id) {
        return userRepository.getPhysician(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> updatePhysicianById(PhysicianDto user, UUID id) {
        try {
            userService.updatePhysicianDtoUserById(user, id);
            additionalPhysicianInfoRepository.updateAdditionalInfoTable(user, id);

            return new ResponseEntity<>("The physician, with id " + id + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The physician with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }

    public Collection<PhyNameOccupationDto> getPhysiciansNamesOccupations() {
        return userRepository.getPhysicians().stream().map(physician ->
                        new PhyNameOccupationDto(
                                physician.getId(),
                                physician.getName(),
                                physician.getOccupation().getName()))
                .collect(Collectors.toList());
    }
}
