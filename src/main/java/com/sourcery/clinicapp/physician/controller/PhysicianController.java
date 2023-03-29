package com.sourcery.clinicapp.physician.controller;

import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.service.PhysicianService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PhysicianController {
    private final PhysicianService physicianService;

    @GetMapping(path = "physicians/{id}")
    public Physician GetPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return physicianService.getPhysicianById(id);
    }

    @PostMapping("physicianInfo")
    public void createPhysician(@RequestBody PhysicianDto physician) {
        physicianService.createPhysician(physician);
    }

    @GetMapping("physicianInfo")
    public List<Physician> getAllPhysiciansWithAdditionalInfo() {
        return physicianService.getAllPhysiciansWithAdditionalInfo();
    }
}
