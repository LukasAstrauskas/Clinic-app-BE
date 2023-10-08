package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import com.sourcery.clinicapp.notifications.EmailSenderService;
import com.sourcery.clinicapp.timeslot.mapper.TimeslotMapper;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.*;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.utils.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TimeslotService {

    @Autowired
    private TimeslotMapper timeslotMapper;
    @Autowired
    private TimeslotDataHelper timeslotDataHelper;
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private LoggedUserService loggedUserService;

    @Autowired
    private UserMapper userMapper;


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

    public Collection<AppointmentDTO> getPatientPastAppointments(int offset) {
        return timeslotMapper.getPatientPastAppointments(loggedUserService.getId(), offset);
    }

    public int getPastAppointmentAmount() {
        return timeslotMapper.getPastAppointmentAmount(loggedUserService.getId());
    }

    public boolean addTimeslot(TimeslotDto timeslotDto) {
        LocalDateTime localDateTime = DateTimeHelper.toDateTime(timeslotDto.date(), timeslotDto.time());
        Timeslot timeslot = new Timeslot(timeslotDto.physicianId(), localDateTime);
        return timeslotMapper.addTimeslot(timeslot);
    }

    public Timeslot getTimeslot(UUID timeslotId) {
        return timeslotMapper.getTimeslot(timeslotId).orElseThrow(() -> new NoSuchElementException("Timeslot was not found."));
    }

    public ResponseEntity<Timeslot> bookAppointment(TimeslotFullDto timeslotDto) {

        int upcomingTimeslotsCount = timeslotMapper.countUpcomingTimeslotsWithPhysician(
                timeslotDto.physicianId(),
                timeslotDto.patientId()
        );
        if (upcomingTimeslotsCount > 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Timeslot timeslot = getTimeslot(timeslotDto.physicianId());
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

    public ResponseEntity<Boolean> deleteTimeslot(UUID timeslotId) {
        boolean deleted = timeslotMapper.deleteTimeslot(timeslotId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(deleted, status);
    }

    public ResponseEntity<Boolean> cancelAppointment(UUID timeslotId) {
        boolean cancelled = timeslotMapper.cancelAppointment(timeslotId);
        return ResponseEntity.status(HttpStatus.OK).body(cancelled);
    }

}
