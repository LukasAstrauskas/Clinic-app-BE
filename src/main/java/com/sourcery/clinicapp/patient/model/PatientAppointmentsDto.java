package com.sourcery.clinicapp.patient.model;

import com.sourcery.clinicapp.occupation.model.Occupation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PatientAppointmentsDto {


    private String physicianId;
    private String physicianName;
    private Occupation occupation;
    private TimeslotForPatient timeslot;

}
