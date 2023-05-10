package com.sourcery.clinicapp.patient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdditionalPatientInfo {
    private UUID userId;
    private String gender;
    private Date birthDate;
    private long phone;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String emergencyName;
    private String emergencyLastName;
    private long emergencyPhone;
    private String emergencyRelation;
}
