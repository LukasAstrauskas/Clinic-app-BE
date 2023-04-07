package com.sourcery.clinicapp.timeslot.model.dto;

import java.util.UUID;

public record NewTimeslotDto(UUID physicianId, String date, String time) {

}
