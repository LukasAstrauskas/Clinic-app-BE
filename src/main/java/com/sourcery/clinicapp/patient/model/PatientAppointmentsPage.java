package com.sourcery.clinicapp.patient.model;

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
