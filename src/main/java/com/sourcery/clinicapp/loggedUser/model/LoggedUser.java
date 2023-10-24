package com.sourcery.clinicapp.loggedUser.model;


import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoggedUser {

    private String id;
    private String name;
    private String surname;
    private String initials;
    private String email;
    private String type;
    private Occupation occupation;
    private PatientInfo patientInfo;
    private Collection<AppointmentDTO> upcomingAppointment;
    private Collection<AppointmentDTO> pastAppointment;
}
