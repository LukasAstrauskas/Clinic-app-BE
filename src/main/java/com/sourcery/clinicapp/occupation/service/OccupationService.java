package com.sourcery.clinicapp.occupation.service;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.repository.OccupationMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OccupationService {

    @Autowired
    private OccupationMapper occupationMapper;

    public Collection<Occupation> getAllOccupations() {
        return occupationMapper.getAllOccupations();
    }

    public Occupation getOccupationById(UUID id) {
        return occupationMapper.getOccupationById(id).orElseThrow(() -> new NoSuchElementException("No occupation with id: " + id));
    }
}
