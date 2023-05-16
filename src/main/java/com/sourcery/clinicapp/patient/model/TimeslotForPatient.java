package com.sourcery.clinicapp.patient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotForPatient {
    private UUID physicianId;
    private LocalDateTime date;
}
