package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.utils.DateTimeHelper;
import com.sourcery.clinicapp.timeslot.mapper.TimeslotMapper;
import com.sourcery.clinicapp.timeslot.model.dto.*;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class TimeslotService {

    @Autowired
    private TimeslotMapper timeslotMapper;

    public Collection<Timeslot> getAllTimeslots() {
        return timeslotMapper.getAltTimeslots();
    }

    public List<TimeslotsDto> getPhyTimeslots(UUID physicianId) {
        List<TimeslotsDto> timeslotsList = new ArrayList<>();
        Collection<Timeslot> physicianTimeslots = timeslotMapper.getPhysicianTimeslots(physicianId);
        Map<String, List<TimeslotDto>> gruopedByDate = physicianTimeslots.stream().map(timeslot -> {
            String date = DateTimeHelper.toDateString(timeslot.getDate());
            String time = DateTimeHelper.toTimeString(timeslot.getDate());
            UUID patientId = timeslot.getPatientId();
            return new TimeslotDto(physicianId, date, time, patientId);
        }).collect(groupingBy(TimeslotDto::date));
        gruopedByDate.forEach((date, timeslotDtos) -> {
            List<TimePatientDto> collect = timeslotDtos.stream()
                    .map(timeslotDto -> new TimePatientDto(
                            timeslotDto.time(),
                            timeslotDto.patientId()
                    )).collect(Collectors.toList());
            timeslotsList.add(new TimeslotsDto(date, collect));
        });
        return timeslotsList;
    }

    public boolean addTimeslot(NewTimeslotDto newTimeslotDto) {
        LocalDateTime localDateTime = DateTimeHelper.toDateTime(newTimeslotDto.date(), newTimeslotDto.time());
        Timeslot timeslot = new Timeslot(newTimeslotDto.physicianId(), localDateTime);
        return timeslotMapper.addTimeslot(timeslot);
    }

    public Timeslot getTimeslot(UUID physicianId, LocalDateTime dateTime) {
        Optional<Timeslot> optional = timeslotMapper.getTimeslot(physicianId, dateTime);
        return optional.orElse(null);
    }

    public boolean deleteTimeslot(UUID physicianId, LocalDateTime dateTime) {
        return timeslotMapper.deleteTimeslot(physicianId, dateTime);
    }
}