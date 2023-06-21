package com.sourcery.clinicapp.physicianInfo.service;

import com.sourcery.clinicapp.physicianInfo.model.PhysicianInfo;
import com.sourcery.clinicapp.physicianInfo.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.repository.PhysicianInfoRepository;
import com.sourcery.clinicapp.user.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public void insertInfo(UUID userId, UUID occupationId) {
        PhysicianInfo physicianInfo = new PhysicianInfo(userId, occupationId);
        physicianInfoRepository.insertPhysicianInfo(physicianInfo);
    }

    public List<Physician> getPhysiciansWithAdditionalInfo() {
        return userMapper.getPhysicians();
    }

    public List<Physician> getLimitedPhysiciansWithAdditionalInfo(Number offset) {
        return userMapper.getLimitedPhysicians(offset);
    }

    public Physician getPhysicianById(UUID id) {
        return userMapper.getPhysician(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> updatePhysicianById(UUID occupationID, UUID physicianID) {
        try {
            physicianInfoRepository.updatePhysicianInfo(occupationID, physicianID);
            return new ResponseEntity<>("The physician, with id " + physicianID + " was updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>("The physician with the provided ID not found.", HttpStatus.NOT_FOUND);
        }
    }

    public Collection<PhyNameOccupationDto> getPhysiciansNamesOccupations() {
        return userMapper.getPhysicians().stream().map(physician ->
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
