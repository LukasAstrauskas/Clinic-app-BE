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

    private UUID id;

    private UUID physicianId;

    private LocalDateTime date;

    private UUID patientId;

    public Timeslot(UUID physicianId, LocalDateTime date) {
        this.physicianId = physicianId;
        this.date = date;
    }

    public Timeslot(UUID physicianId, LocalDateTime date, UUID patientId) {
        this.physicianId = physicianId;
        this.date = date;
        this.patientId = patientId;
    }
}
