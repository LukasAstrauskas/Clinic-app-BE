package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.utils.DateTimeHelper;
import com.sourcery.clinicapp.timeslot.mapper.TimeslotMapper;
import com.sourcery.clinicapp.timeslot.model.dto.*;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Map<String, List<TimeslotFullDto>> gruopedByDate = physicianTimeslots.stream().map(timeslot -> {
            String date = DateTimeHelper.toDateString(timeslot.getDate());
            String time = DateTimeHelper.toTimeString(timeslot.getDate());
            UUID patientId = timeslot.getPatientId();
            return new TimeslotFullDto(physicianId, date, time, patientId);
        }).collect(groupingBy(TimeslotFullDto::date));
        gruopedByDate.forEach((date, timeslotDtos) -> {
            List<TimePatientDto> collect = timeslotDtos.stream()
                    .map(timeslotFullDto -> new TimePatientDto(
                            timeslotFullDto.time(),
                            timeslotFullDto.patientId()
                    )).collect(Collectors.toList());
            timeslotsList.add(new TimeslotsDto(date, collect));
        });
        return timeslotsList;
    }

    public boolean addTimeslot(TimeslotDto timeslotDto) {
        LocalDateTime localDateTime = DateTimeHelper.toDateTime(timeslotDto.date(), timeslotDto.time());
        Timeslot timeslot = new Timeslot(timeslotDto.physicianId(), localDateTime);
        return timeslotMapper.addTimeslot(timeslot);
    }

    public Optional<Timeslot> getTimeslot(UUID physicianId, LocalDateTime dateTime) {
        return timeslotMapper.getTimeslot(physicianId, dateTime);
    }

    public ResponseEntity<Timeslot> updateTimeslot(TimeslotFullDto timeslotDto) {

        Short upcomingTimeslotsCount = timeslotMapper.countUpcomingTimeslotsWithPhysician(
                timeslotDto.physicianId(),
                timeslotDto.patientId()
        );
        if (upcomingTimeslotsCount > 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Timeslot> optional = getTimeslot(
                timeslotDto.physicianId(),
                DateTimeHelper.toDateTime(timeslotDto.date(), timeslotDto.time())
        );
        Timeslot timeslot = optional.orElseThrow(() -> new NoSuchElementException("Timeslot was not found."));
        timeslot.setPatientId(timeslotDto.patientId());
        boolean updated = timeslotMapper.updateTimeslotSetPatientID(timeslot);
        HttpStatus status = updated
                ? HttpStatus.CREATED
                : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<Timeslot>(timeslot, status);
    }

    public ResponseEntity<Timeslot> deleteTimeslot(TimeslotDto timeslotDto) {
        Optional<Timeslot> optional = getTimeslot(
                timeslotDto.physicianId(),
                DateTimeHelper.toDateTime(timeslotDto.date(), timeslotDto.time())
        );
        Timeslot timeslot = optional.orElseThrow(() -> new NoSuchElementException("Timeslot was not found."));
        boolean deleted = timeslotMapper.deleteTimeslot(timeslot);
        HttpStatus status = deleted
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
        new ResponseEntity<Timeslot>(timeslot, status);
        return new ResponseEntity<Timeslot>(timeslot, status);
    }

    public ResponseEntity<Void> removePatientFromTimeslot(UUID physicianId, UUID patientId) {
        boolean isDeleted  = timeslotMapper.removePatientFromTimeslot(physicianId, patientId);
        if (isDeleted  == true) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
