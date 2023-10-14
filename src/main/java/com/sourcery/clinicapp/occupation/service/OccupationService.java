package com.sourcery.clinicapp.occupation.service;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.repository.OccupationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class OccupationService {

    @Autowired
    private OccupationMapper occupationMapper;

    public Collection<Occupation> getAllOccupations() {
        return occupationMapper.getAllOccupations();
    }

    public Occupation getOccupationById(String id) {
        return occupationMapper.getOccupationById(id).orElseThrow(() -> new NoSuchElementException("No occupation with id: " + id));
    }
}
