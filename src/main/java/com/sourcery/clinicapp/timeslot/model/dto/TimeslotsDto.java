package com.sourcery.clinicapp.timeslot.model.dto;

import java.time.LocalDate;
import java.util.Collection;

public record TimeslotsDto(String date, Collection<TimePatientDto> timePatientList) {

}
