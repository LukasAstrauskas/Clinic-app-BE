package com.sourcery.clinicapp.patientInfo.service;

import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
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

    public PatientInfo getPatientInfo(UUID id) {
        try {
            PatientInfo patientInfo = patientInfoRepository.getPatientInfo(id).orElseThrow(
                    () -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
            return patientInfo;
        } catch (HttpServerErrorException e) {
            return new PatientInfo();
        }
    }

    public ResponseEntity<PatientInfo> updatePatientInfo(UUID id, PatientInfo patientInfo) {
        try {
            if (!patientInfoRepository.updatePatientInfo(id, patientInfo)) {
                return new ResponseEntity<>(patientInfo, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(patientInfo, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(patientInfo, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(patientInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<PatientInfo> createPatientInfo(PatientInfo patientInfo) {
        try {
            if (!patientInfoRepository.createPatientInfo(patientInfo)) {
                return new ResponseEntity<>(patientInfo, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(patientInfo, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(patientInfo, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(patientInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
