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
    private UUID user_id;
    private String gender;
    private Date birth_date;
    private long phone;
    private String street;
    private String city;
    private String postal_code;
    private String country;
    private String emergency_name;
    private String emergency_surname;
    private long emergency_phone;
    private String emergency_relation;
}
