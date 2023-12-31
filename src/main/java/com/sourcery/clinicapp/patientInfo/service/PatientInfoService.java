package com.sourcery.clinicapp.patientInfo.service;

import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import com.sourcery.clinicapp.patientInfo.repository.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.NoSuchElementException;

@Service
public class PatientInfoService {

    @Autowired
    private PatientInfoRepository patientInfoRepository;

    @Autowired
    private LoggedUserService loggedUserService;

    public PatientInfo getPatientInfo(String id) {
        try {
            return patientInfoRepository.getPatientInfo(id).orElseThrow(
                    () -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
        } catch (HttpServerErrorException e) {
            return new PatientInfo();
        }
    }

    public ResponseEntity<PatientInfo> updatePatientInfo(PatientInfo patientInfo) {
        try {
            if (!patientInfoRepository.updatePatientInfo( patientInfo)) {
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
