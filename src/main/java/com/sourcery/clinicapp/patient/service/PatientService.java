package com.sourcery.clinicapp.patient.service;

import com.sourcery.clinicapp.patient.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patient.repository.AdditionalPatientInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientService {

    private final AdditionalPatientInfoRepository additionalPatientInfoRepository;
    public AdditionalPatientInfo getAdditionalPatientInfo(UUID id) {
        return additionalPatientInfoRepository.getAdditionalPatientInfo(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<AdditionalPatientInfo> updateAdditionalPatientInfo(UUID id, AdditionalPatientInfo additionalPatientInfo) {
        try{
            boolean result = additionalPatientInfoRepository.updateAdditionalPatientInfo(id, additionalPatientInfo);
            if(result)
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.OK);
            else
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        }
        catch (NoSuchElementException exception){
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<AdditionalPatientInfo> createAdditionalPatientInfo(AdditionalPatientInfo additionalPatientInfo) {
        try{
            boolean result = additionalPatientInfoRepository.createAdditionalPatientInfo(additionalPatientInfo);
            if(result)
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.OK);
            else
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        }
        catch (NoSuchElementException exception){
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        }
    }
}
