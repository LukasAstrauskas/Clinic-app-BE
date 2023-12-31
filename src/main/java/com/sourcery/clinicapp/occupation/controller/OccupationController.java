package com.sourcery.clinicapp.occupation.controller;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("occupation")
public class OccupationController {

    @Autowired
    private OccupationService occupationService;

    @GetMapping("/all")
    public Collection<Occupation> getAllOccupations() {
        return occupationService.getAllOccupations();
    }

    @GetMapping("/{id}")
    public Occupation getOccupationById(@PathVariable("id") String id) {
        return occupationService.getOccupationById(id);
    }

    @GetMapping("add/{name}")
    public String addOccupationMock(@PathVariable String name) {
        return String.format("Added new occupation: %s", name);
    }
}
