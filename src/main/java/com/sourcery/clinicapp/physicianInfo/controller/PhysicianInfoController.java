package com.sourcery.clinicapp.physicianInfo.controller;

import com.sourcery.clinicapp.physicianInfo.model.PhyNameOccupationDto;
import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.service.PhysicianInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/physicianInfo")
public class PhysicianInfoController {

    private final PhysicianInfoService physicianInfoService;

    @GetMapping("{id}")
    public Physician getPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return physicianInfoService.getPhysicianById(id);
    }


    @GetMapping("physcians/offset/{offset}")
    public List<Physician> getLimitedPhysicians(@PathVariable("offset") Number offset) {
        return physicianInfoService.getLimitedPhysiciansWithAdditionalInfo(offset);
    }

    @GetMapping()
    public List<Physician> getPhysiciansWithAdditionalInfo() {
        return physicianInfoService.getPhysiciansWithAdditionalInfo();
    }

    @GetMapping("physicianNamesOccupations")
    public Collection<PhyNameOccupationDto> getPhysiciansNamesOccupations() {
        return physicianInfoService.getPhysiciansNamesOccupations();
    }
}
