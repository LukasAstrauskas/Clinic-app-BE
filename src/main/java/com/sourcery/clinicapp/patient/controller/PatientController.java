package com.sourcery.clinicapp.patient.controller;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patient.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/patientInfo")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("{id}")
    public AdditionalPatientInfo getPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return patientService.getAdditionalPatientInfo(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<AdditionalPatientInfo> updateAdditionalPatientInfo(@PathVariable UUID id, @RequestBody AdditionalPatientInfo updatedPatientInfo) {
        return patientService.updateAdditionalPatientInfo(id, updatedPatientInfo);
    }

    @PostMapping("/")
    public ResponseEntity<AdditionalPatientInfo> createPatient(@RequestBody AdditionalPatientInfo patient) {
        return patientService.createAdditionalPatientInfo(patient);
    }
}