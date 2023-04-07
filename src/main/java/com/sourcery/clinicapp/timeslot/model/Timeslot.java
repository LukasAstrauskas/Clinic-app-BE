package com.sourcery.clinicapp.timeslot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {

    private UUID physicianId;

    private LocalDateTime date;

    private UUID patientId;

    public Timeslot(UUID physicianId, LocalDateTime date) {
        this.physicianId = physicianId;
        this.date = date;
    }
}
