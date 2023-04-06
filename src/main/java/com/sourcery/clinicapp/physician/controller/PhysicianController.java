package com.sourcery.clinicapp.physician.controller;

import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.physician.service.PhysicianService;
import com.sourcery.clinicapp.user.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/physicianInfo")
public class PhysicianController {
    private final PhysicianService physicianService;

    @GetMapping("{id}")
    public Physician GetPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return physicianService.getPhysicianById(id);
    }

    @PostMapping()
    public void createPhysician(@RequestBody PhysicianDto physician) {
        physicianService.createPhysician(physician);
    }

    @GetMapping()
    public List<Physician> getAllPhysiciansWithAdditionalInfo() {
        return physicianService.getAllPhysiciansWithAdditionalInfo();
    }
    @PutMapping("{uuid}")
    public ResponseEntity<String> updatePhysicianById(@RequestBody PhysicianDto user, @PathVariable("uuid") UUID uuid) {
        return physicianService.updatePhysicianById(user, uuid);
    }
}
