package com.sourcery.clinicapp.patientInfo.service;

import com.sourcery.clinicapp.patientInfo.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.patientInfo.repository.PatientInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientInfoService {

    private final PatientInfoRepository patientInfoRepository;

    public AdditionalPatientInfo getPatientInfo(UUID id) {
        try {
            AdditionalPatientInfo additionalPatientInfo = patientInfoRepository.getPatientInfo(id).orElseThrow(
                    () -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
            return additionalPatientInfo;
        } catch (HttpServerErrorException e) {
            return new AdditionalPatientInfo();
        }
    }

    public ResponseEntity<AdditionalPatientInfo> updatePatientInfo(UUID id, AdditionalPatientInfo additionalPatientInfo) {
        try {
            if (!patientInfoRepository.updatePatientInfo(id, additionalPatientInfo)) {
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<AdditionalPatientInfo> createPatientInfo(AdditionalPatientInfo additionalPatientInfo) {
        try {
            if (!patientInfoRepository.createPatientInfo(additionalPatientInfo)) {
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
