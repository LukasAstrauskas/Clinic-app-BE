package com.sourcery.clinicapp.timeslot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotDTO {
    private String id;

    private String physicianId;

    private String date;

    private String patientId;
}
