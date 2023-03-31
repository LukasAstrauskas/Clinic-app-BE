package com.sourcery.clinicapp.occupation.service;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.repository.OccupationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OccupationService {
    private final OccupationRepository occupationRepository;

    public List<Occupation> getAllOccupations() {
        return occupationRepository.getAllOccupations();
    }

    public Occupation getOccupationById(UUID id) {
        return occupationRepository.findById(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
