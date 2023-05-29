package com.sourcery.clinicapp.patientInfo.model;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientAppointmentsPage<T> {
    private List<T> data;
    private int total;
}
