package com.sourcery.clinicapp.timeslot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timeslot {

    private String id;

    private String physicianId;

    private LocalDateTime date;

    private String  patientId;

}
