package com.sourcery.clinicapp.timeslot.model.dto;

import java.util.UUID;

public record TimeslotDto(UUID physicianId, String date, String time) {

}
