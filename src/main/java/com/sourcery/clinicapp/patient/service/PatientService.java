package com.sourcery.clinicapp.patient.service;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patient.repository.AdditionalPatientInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientService {

    private final AdditionalPatientInfoRepository additionalPatientInfoRepository;
    public AdditionalPatientInfo getAdditionalPatientInfo(UUID id) {
        return additionalPatientInfoRepository.getAdditionalPatientInfo(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
