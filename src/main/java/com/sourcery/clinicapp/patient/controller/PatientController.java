package com.sourcery.clinicapp.patient.controller;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/patientInfo")
public class PatientController {
    private final PatientService patientService;
    @GetMapping("{id}")
    public AdditionalPatientInfo GetPhysicianWithAdditionalInfo(@PathVariable("id") UUID id) {
        return patientService.getAdditionalPatientInfo(id);
    }
}
