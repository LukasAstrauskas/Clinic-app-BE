package com.sourcery.clinicapp.user.model;


import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.patientInfo.model.AdditionalPatientInfo;
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
    private UUID id;
    private String name;
    private String surname;
    private String initials;
    private String email;
    private String type;
    private Occupation occupation;
    private AdditionalPatientInfo patientInfo;
    private Collection<AppointmentDTO> upcomingAppointment;
}
