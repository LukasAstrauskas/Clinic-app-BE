package com.sourcery.clinicapp.physician.controller;

import com.sourcery.clinicapp.physician.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.repository.AdditionalPhysicianInfoRepository;
import com.sourcery.clinicapp.physician.service.PhysicianService;
import com.sourcery.clinicapp.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PhysicianController {

    private final PhysicianService physicianService;
    private final AdditionalPhysicianInfoRepository temp;

    @GetMapping(path = "physician/{id}")
    public Physician GetPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return physicianService.getPhysicianById(id);
    }


    @GetMapping(value = "physcians/limit/{limit}")
    public List<Physician> getLimitedPhysicians(@PathVariable("limit") Number limit){
        return physicianService.getLimitedPhysiciansWithAdditionalInfo(limit);
    }


    @PostMapping("physicianInfo")
    public void createPhysician(@RequestBody PhysicianDto physician) {
        physicianService.createPhysician(physician);
    }

    @GetMapping("physicianInfo")
    public List<Physician> getAllPhysiciansWithAdditionalInfo() {
        return physicianService.getAllPhysiciansWithAdditionalInfo();
    }

    @DeleteMapping(value = "physician/{id}")
    void DeletePhysicianById(@PathVariable("id") UUID id)
    {
        temp.deletePhysicianById(id);
    }

    @GetMapping("physicianNamesOccupations")
    public Collection<PhyNameOccupationDto> getPhysiciansNamesOccupations() {
        return physicianService.getPhysiciansNamesOccupations();
    }
}
