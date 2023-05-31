package com.sourcery.clinicapp.physicianInfo.service;

import com.sourcery.clinicapp.physicianInfo.model.PhysicianInfo;
import com.sourcery.clinicapp.physicianInfo.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.model.PhysicianDto;
import com.sourcery.clinicapp.physicianInfo.repository.PhysicianInfoRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhysicianInfoService {

    private final PhysicianInfoRepository physicianInfoRepository;
    private final UserRepository userRepository;

    public void insertInfo(UUID userId,UUID occupationId ) {
        PhysicianInfo physicianInfo = new PhysicianInfo(userId, occupationId);
        physicianInfoRepository.insertPhysicianInfo(physicianInfo);
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
            physicianInfoRepository.updatePhysicianInfo(user, id);
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
