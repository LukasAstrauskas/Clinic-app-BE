package com.sourcery.clinicapp.physician.service;

import com.sourcery.clinicapp.physician.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.repository.AdditionalPhysicianInfoRepository;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PhysicianService {

    private final AdditionalPhysicianInfoRepository additionalPhysicianInfoRepository;
    private final UserRepository userRepository;

    public void createPhysician(PhysicianDto physicianDto) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(physicianDto.getName())
                .email(physicianDto.getEmail())
                .password(physicianDto.getPassword())
                .type("physician")
                .build();
        userRepository.save(user);

        AdditionalPhysicianInfo info = AdditionalPhysicianInfo.builder()
                .userId(user.getId())
                .occupationId(physicianDto.getOccupationId())
                .build();
        additionalPhysicianInfoRepository.insertInfo(info);
    }

    public List<Physician> getAllPhysiciansWithAdditionalInfo() {
        return userRepository.getAllPhysicians();
    }

    public Physician getPhysicianById(UUID id) {
        return userRepository.getPhysician(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
