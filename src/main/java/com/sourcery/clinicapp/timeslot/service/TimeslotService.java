package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.notifications.EmailSenderService;
import com.sourcery.clinicapp.utils.DateTimeHelper;
import com.sourcery.clinicapp.timeslot.mapper.TimeslotMapper;
import com.sourcery.clinicapp.timeslot.model.dto.*;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TimeslotService {

    @Autowired
    private TimeslotMapper timeslotMapper;
    @Autowired
    private TimeslotDataHelper timeslotDataHelper;
    @Autowired
    private EmailSenderService emailSenderService;

    public Collection<Timeslot> getAllTimeslots() {
        return timeslotMapper.getAltTimeslots();
    }

    public ResponseEntity<List<TimeslotsDto>> getPhyTimeslots(UUID physicianId, String startDate) {
        System.out.println("Timeslots, start date: " + startDate);
        LocalDateTime begin = DateTimeHelper.fromDateString(startDate);
        LocalDateTime end = DateTimeHelper.nextMonthFirstDay(begin);
        Collection<Timeslot> physicianTimeslots = timeslotMapper.getPhysicianTimeslots(physicianId, begin, end);
        List<TimeslotsDto> timeslotsDTOs = timeslotDataHelper.groupTimeslotsByDate(physicianTimeslots, physicianId);
        return new ResponseEntity<>(timeslotsDTOs, HttpStatus.OK);
    }

    public ResponseEntity<List<TimeslotByDate>> getMonthsTimeslots(UUID physicianId, String startDate) {
        List<TimeslotByDate> timeslotByDateList = new ArrayList<>();
        LocalDateTime begin = DateTimeHelper.fromDateString(startDate);
        LocalDateTime end = DateTimeHelper.nextMonthFirstDay(begin);
        Map<LocalDate, List<Timeslot>> groupByDate = timeslotMapper.getMonthsTimeslots(physicianId, begin, end)
                .stream().collect(Collectors.groupingBy(timeslot ->
                        timeslot.getDate().toLocalDate()
                ));
        groupByDate.forEach(((localDate, timeslots) -> {
            TimeslotByDate timeslotByDate = new TimeslotByDate(localDate, timeslots);
            timeslotByDateList.add(timeslotByDate);
        }));
        timeslotByDateList.sort(Comparator.comparing(TimeslotByDate::getLocalDate));
        return new ResponseEntity<>(timeslotByDateList, HttpStatus.OK);
    }

    public Collection<AppointmentDTO> getPatientUpcomingAppointments(UUID id) {
        return timeslotMapper.getPatientUpcomingAppointments(id);
    }

    public Collection<AppointmentDTO> getPatientPastAppointments(UUID patientID, int offset) {
        return timeslotMapper.getPatientPastAppointments(patientID, offset);
    }

    public int getPastAppointmentAmount(UUID patientID) {
        return timeslotMapper.getPastAppointmentAmount(patientID);
    }

    public boolean addTimeslot(TimeslotDto timeslotDto) {
        LocalDateTime localDateTime = DateTimeHelper.toDateTime(timeslotDto.date(), timeslotDto.time());
        Timeslot timeslot = new Timeslot(timeslotDto.physicianId(), localDateTime);
        return timeslotMapper.addTimeslot(timeslot);
    }

    public Optional<Timeslot> getTimeslot(UUID physicianId, LocalDateTime dateTime) {
        return timeslotMapper.getTimeslot(physicianId, dateTime);
    }

    public ResponseEntity<Timeslot> bookAppointment(TimeslotFullDto timeslotDto) {

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

        if (updated) {
            emailSenderService.getEmailMessage(timeslotDto);
        }

        HttpStatus status = updated
                ? HttpStatus.OK
                : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(timeslot, status);
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
        new ResponseEntity<>(timeslot, status);
        return new ResponseEntity<>(timeslot, status);
    }


    public ResponseEntity<Void> removePatientFromUpcomingTimeslot(UUID physicianId, UUID patientId) {
        boolean isDeleted = timeslotMapper.removePatientFromUpcomingTimeslot(physicianId, patientId);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> removePatientFromTimeslot(TimeslotFullDto timeslotfullDto) {
        Optional<Timeslot> optional = getTimeslot(
                timeslotfullDto.physicianId(),
                DateTimeHelper.toDateTime(timeslotfullDto.date(), timeslotfullDto.time())
        );
        Timeslot timeslot = optional.orElseThrow(() -> new NoSuchElementException("Timeslot was not found."));
        timeslot.setPatientId(timeslotfullDto.patientId());
        boolean isDeleted = timeslotMapper.removePatientFromTimeslot(timeslot);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
