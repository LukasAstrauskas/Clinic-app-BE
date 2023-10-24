package com.sourcery.clinicapp.patientInfo.controller;

import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import com.sourcery.clinicapp.patientInfo.service.PatientInfoService;
import com.sourcery.clinicapp.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/patientInfo")
public class PatientInfoController {

    @Autowired
    private PatientInfoService patientInfoService;

    @GetMapping("{id}")
    public PatientInfo getPatientInfo(@PathVariable("id") String id) {
        return patientInfoService.getPatientInfo(id);
    }

    @PutMapping()
    public ResponseEntity<PatientInfo> updatePatientInfo(@RequestBody PatientInfo patientInfo) {
        return patientInfoService.updatePatientInfo(patientInfo);
    }

    @PostMapping("/")
    public ResponseEntity<PatientInfo> createPatientInfo(@RequestBody PatientInfo patientInfo) {
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