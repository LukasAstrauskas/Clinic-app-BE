package com.sourcery.clinicapp.patientInfo.controller;

import com.sourcery.clinicapp.patientInfo.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patientInfo.service.PatientInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/patientInfo")
public class PatientInfoController {
    private final PatientInfoService patientInfoService;

    @GetMapping("{id}")
    public AdditionalPatientInfo getPatientInfo(@PathVariable("id") UUID id) {
        return patientInfoService.getPatientInfo(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<AdditionalPatientInfo> updatePatientInfo(@PathVariable UUID id, @RequestBody AdditionalPatientInfo patientInfo) {
        return patientInfoService.updatePatientInfo(id, patientInfo);
    }

    @PostMapping("/")
    public ResponseEntity<AdditionalPatientInfo> createPatientInfo(@RequestBody AdditionalPatientInfo patientInfo) {
        return patientInfoService.createPatientInfo(patientInfo);
    }
}