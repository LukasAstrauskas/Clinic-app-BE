package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import com.sourcery.clinicapp.notifications.EmailSenderService;
import com.sourcery.clinicapp.timeslot.mapper.TimeslotMapper;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.TimeslotDTO;
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
    private EmailSenderService emailSenderService;

    @Autowired
    private LoggedUserService loggedUserService;

    @Autowired
    private UserMapper userMapper;


    public Collection<Timeslot> getAllTimeslots() {
        return timeslotMapper.getAltTimeslots();
    }

    public ResponseEntity<List<TimeslotList>> getMonthsTimeslots(UUID physicianId, String startDate) {
        List<TimeslotList> groupedTimeslotList = new ArrayList<>();
        LocalDateTime begin = DateTimeHelper.fromDateString(startDate);
        LocalDateTime end = DateTimeHelper.nextMonthFirstDay(begin);
        Map<LocalDate, List<Timeslot>> groupByDate = timeslotMapper.getMonthsTimeslots(physicianId, begin, end)
                .stream().collect(Collectors.groupingBy(timeslot ->
                        timeslot.getDate().toLocalDate()
                ));
        groupByDate.forEach(((localDate, timeslots) -> {
            TimeslotList timeslotList = new TimeslotList(localDate, timeslots);
            groupedTimeslotList.add(timeslotList);
        }));
        groupedTimeslotList.sort(Comparator.comparing(TimeslotList::getDate));
        return new ResponseEntity<>(groupedTimeslotList, HttpStatus.OK);
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

    public boolean addTimeslot(TimeslotDTO timeslotDto) {
        LocalDateTime localDateTime = DateTimeHelper.toDateTime(timeslotDto.getDate(), timeslotDto.getTime());
        Timeslot timeslot = Timeslot.builder()
                .id(UUID.randomUUID())
                .physicianId(timeslotDto.getPhysicianId())
                .date(localDateTime)
                .build();
        return timeslotMapper.addTimeslot(timeslot);
    }

    public Timeslot getTimeslot(UUID timeslotId) {
        return timeslotMapper.getTimeslot(timeslotId).orElseThrow(() -> new NoSuchElementException("Timeslot was not found."));
    }

    public ResponseEntity<Boolean> bookAppointment(TimeslotDTO timeslotDto) {
        UUID physicianId = timeslotDto.getPhysicianId();
        UUID patientId = timeslotDto.getPatientId();
        int upcomingTimeslotsCount = timeslotMapper.countUpcomingTimeslotsWithPhysician(
                physicianId,
                patientId
        );
        if (upcomingTimeslotsCount > 0) {
            return ResponseEntity.badRequest().body(null);
        }

        boolean updated = timeslotMapper.updateTimeslotSetPatientID(timeslotDto.getId(), patientId);

        if (updated) {
            emailSenderService.getEmailMessage(timeslotDto);
        }

        HttpStatus status = updated
                ? HttpStatus.OK
                : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(updated, status);
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
