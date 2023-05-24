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
        try {
            AdditionalPatientInfo additionalPatientInfo = additionalPatientInfoRepository.getAdditionalPatientInfo(id).orElseThrow(
                    () -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
            return additionalPatientInfo;
        } catch (HttpServerErrorException e) {
            return new AdditionalPatientInfo();
        }
    }

    public ResponseEntity<AdditionalPatientInfo> updateAdditionalPatientInfo(UUID id, AdditionalPatientInfo additionalPatientInfo) {
        try {
            if (!additionalPatientInfoRepository.updateAdditionalPatientInfo(id, additionalPatientInfo)) {
                return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(additionalPatientInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<AdditionalPatientInfo> createAdditionalPatientInfo(AdditionalPatientInfo additionalPatientInfo) {
        try {
            if (!additionalPatientInfoRepository.createAdditionalPatientInfo(additionalPatientInfo)) {
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
