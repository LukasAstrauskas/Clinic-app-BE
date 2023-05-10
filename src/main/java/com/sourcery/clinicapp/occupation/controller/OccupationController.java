package com.sourcery.clinicapp.occupation.controller;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.service.OccupationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class OccupationController {

    private final OccupationService occupationService;

    @GetMapping("occupations")
    public List<Occupation> GetAllOccupations() {
        return occupationService.getAllOccupations();
    }

    @GetMapping("{id}")
    public Occupation GetOccupationById (@PathVariable("id") UUID id){
        return occupationService.getOccupationById(id);
    }

}
