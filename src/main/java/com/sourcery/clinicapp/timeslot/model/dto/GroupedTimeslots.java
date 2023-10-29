package com.sourcery.clinicapp.timeslot.model.dto;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupedTimeslots {

    private LocalDate date;
    private List<Timeslot> timeslots;
}
