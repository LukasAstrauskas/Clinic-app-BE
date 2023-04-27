package com.sourcery.clinicapp.patient.controller;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/patientInfo")
public class PatientController {
    private final PatientService patientService;
    @GetMapping("{id}")
    public AdditionalPatientInfo getPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return patientService.getAdditionalPatientInfo(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable UUID id, @RequestBody AdditionalPatientInfo updatedPatientInfo) {
        return patientService.updateAdditionalPatientInfo(id, updatedPatientInfo);
    }
}
