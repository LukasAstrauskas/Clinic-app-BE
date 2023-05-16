package com.sourcery.clinicapp.patient.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientAppointmentsPage<T> {
    private List<T> data;
    private int total;
}
