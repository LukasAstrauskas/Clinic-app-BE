package com.sourcery.clinicapp.physician.controller;

import com.sourcery.clinicapp.physician.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.repository.AdditionalPhysicianInfoRepository;
import com.sourcery.clinicapp.physician.service.PhysicianService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/physicianInfo")
public class PhysicianController {

    private final PhysicianService physicianService;
    private final AdditionalPhysicianInfoRepository temp;

    @GetMapping("{id}")
    public Physician GetPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return physicianService.getPhysicianById(id);
    }


    @GetMapping(value = "physcians/offset/{offset}")
    public List<Physician> getLimitedPhysicians(@PathVariable("offset") Number offset) {
        return physicianService.getLimitedPhysiciansWithAdditionalInfo(offset);
    }


    @PostMapping()
    public void createPhysician(@RequestBody PhysicianDto physician) {
        physicianService.createPhysician(physician);
    }

    @GetMapping()
    public List<Physician> getPhysiciansWithAdditionalInfo() {
        return physicianService.getPhysiciansWithAdditionalInfo();
    }

    @PutMapping("{uuid}")
    public ResponseEntity<String> updatePhysicianById(@RequestBody PhysicianDto user, @PathVariable("uuid") UUID uuid) {
        return physicianService.updatePhysicianById(user, uuid);
    }

    @DeleteMapping(value = "physician/{id}")
    void DeletePhysicianById(@PathVariable("id") UUID id) {
        temp.deletePhysicianById(id);
    }

    @GetMapping("physicianNamesOccupations")
    public Collection<PhyNameOccupationDto> getPhysiciansNamesOccupations() {
        return physicianService.getPhysiciansNamesOccupations();
    }
}
