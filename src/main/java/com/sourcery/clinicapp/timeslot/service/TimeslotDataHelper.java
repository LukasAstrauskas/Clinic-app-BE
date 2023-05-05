package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.TimePatientDto;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotFullDto;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotsDto;
import com.sourcery.clinicapp.utils.DateTimeHelper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class TimeslotDataHelper {

    public List<TimeslotsDto> groupTimeslotsByDate(Collection<Timeslot> physicianTimeslots, UUID physicianId) {
        List<TimeslotsDto> timeslotsList = new ArrayList<>();
        Map<String, List<TimeslotFullDto>> groupedByDate = physicianTimeslots.stream().map(timeslot -> {
            String date = DateTimeHelper.toDateString(timeslot.getDate());
            String time = DateTimeHelper.toTimeString(timeslot.getDate());
            UUID patientId = timeslot.getPatientId();
            return new TimeslotFullDto(physicianId, date, time, patientId);
        }).collect(groupingBy(TimeslotFullDto::date));
        groupedByDate.forEach((date, timeslotDTOs) -> {
            List<TimePatientDto> collect = timeslotDTOs.stream()
                    .map(timeslotFullDto -> new TimePatientDto(
                            timeslotFullDto.time(),
                            timeslotFullDto.patientId()
                    )).collect(Collectors.toList());
            timeslotsList.add(new TimeslotsDto(date, collect));
        });
        return timeslotsList.stream().sorted(Comparator.comparing(TimeslotsDto::date)).collect(Collectors.toList());
    }

}
