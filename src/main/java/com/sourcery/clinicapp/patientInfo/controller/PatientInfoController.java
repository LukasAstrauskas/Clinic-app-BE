package com.sourcery.clinicapp.patientInfo.controller;

import com.sourcery.clinicapp.patientInfo.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patientInfo.service.PatientInfoService;
import com.sourcery.clinicapp.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("patient")
    public String getInfoAbout() {
        String role = "";
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        role = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        System.out.println("name(): " + User.PATIENT.name());
        System.out.println("Plain enum: " + User.PATIENT);
        System.out.println("value(): " + User.PATIENT.authority());
        return "Your role: " + role;
    }

    @GetMapping("admin")
    public String getInfoAboutAdmin() {
        String role;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        role = authorities.stream().findFirst().orElseThrow().getAuthority();
        return String.format("Hello, %s. Your role: %s.", auth.getName(), role);
    }
}