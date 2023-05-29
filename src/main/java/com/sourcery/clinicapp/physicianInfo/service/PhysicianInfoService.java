package com.sourcery.clinicapp.physicianInfo.service;

import com.sourcery.clinicapp.physicianInfo.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.physicianInfo.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.model.PhysicianDto;
import com.sourcery.clinicapp.physicianInfo.repository.PhysicianInfoRepository;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.repository.UserRepository;
import com.sourcery.clinicapp.user.service.UserService;
import com.sourcery.clinicapp.utils.UserFieldHelper;
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
public class PhysicianInfoService {

    private final PhysicianInfoRepository physicianInfoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserFieldHelper userFieldHelper;
    private final PasswordEncoder encoder;

    public void createPhysician(PhysicianDto physicianDto) {
        String capitalizedFullName = userFieldHelper.capitalizeFullName(physicianDto.getName());

        User user = User.builder()
                .id(UUID.randomUUID())
                .name(capitalizedFullName)
                .email(physicianDto.getEmail())
                .password(encoder.encode(physicianDto.getPassword()))
                .type("physician")
                .build();
        userRepository.saveUser(user);
        AdditionalPhysicianInfo info = AdditionalPhysicianInfo.builder()
                .userId(user.getId())
                .occupationId(physicianDto.getOccupationId())
                .build();
        physicianInfoRepository.insertInfo(info);
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
            physicianInfoRepository.updateAdditionalInfoTable(user, id);

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

    public void deletePhysicianInfo(UUID id) {
        physicianInfoRepository.deletePhysicianInfo(id);
    }
}
