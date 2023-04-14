package com.sourcery.clinicapp.timeslot.model.dto;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.utils.DateTimeHelper;

import java.time.LocalDateTime;
import java.util.UUID;

public record TimeslotFullDto(UUID physicianId, String date, String time, UUID patientId) {

    public Timeslot toTimeslot() {
        LocalDateTime localDateTime = DateTimeHelper.toDateTime(this.date, this.time);
        return new Timeslot(this.physicianId, localDateTime, this.patientId);
    }
}
