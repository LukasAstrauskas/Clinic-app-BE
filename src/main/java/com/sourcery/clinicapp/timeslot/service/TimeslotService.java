package com.sourcery.clinicapp.timeslot.service;

import com.sourcery.clinicapp.loggedUser.service.LoggedUserService;
import com.sourcery.clinicapp.notifications.EmailSenderService;
import com.sourcery.clinicapp.timeslot.mapper.TimeslotMapper;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotDTO;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotId;
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

    public ResponseEntity<List<GroupedTimeslots>> getMonthsTimeslots(String physicianId, String startDate) {
        List<GroupedTimeslots> groupedGroupedTimeslots = new ArrayList<>();
        LocalDateTime begin = DateTimeHelper.fromDateString(startDate);
        LocalDateTime end = DateTimeHelper.nextMonthFirstDay(begin);
        Map<LocalDate, List<Timeslot>> groupByDate = timeslotMapper.getMonthsTimeslots(physicianId, begin, end)
                .stream().collect(Collectors.groupingBy(timeslot ->
                        timeslot.getDate().toLocalDate()
                ));
        groupByDate.forEach(((localDate, timeslots) -> {
            GroupedTimeslots groupedTimeslots = new GroupedTimeslots(localDate, timeslots);
            groupedGroupedTimeslots.add(groupedTimeslots);
        }));
        groupedGroupedTimeslots.sort(Comparator.comparing(GroupedTimeslots::getDate));
        return new ResponseEntity<>(groupedGroupedTimeslots, HttpStatus.OK);
    }

    public Collection<AppointmentDTO> getPatientUpcomingAppointments() {
        return timeslotMapper.getPatientUpcomingAppointments(loggedUserService.getId());
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
                .id(UUID.randomUUID().toString())
                .physicianId(timeslotDto.getPhysicianId())
                .date(localDateTime)
                .build();
        return timeslotMapper.addTimeslot(timeslot);
    }

    public Timeslot getTimeslot(String timeslotId) {
        return timeslotMapper.getTimeslot(timeslotId).orElseThrow(() -> new NoSuchElementException("Timeslot was not found."));
    }

    public Collection<AppointmentDTO> bookAppointment(Timeslot timeslot) {
        String physicianId = timeslot.getPhysicianId();
        String patientId = timeslot.getPatientId();
        int upcomingTimeslotsCount = timeslotMapper.countUpcomingTimeslotsWithPhysician(
                physicianId,
                patientId
        );
        if (upcomingTimeslotsCount > 0) {
            return Collections.EMPTY_LIST;
        }

        boolean updated = timeslotMapper.updateTimeslotSetPatientID(timeslot.getId(), patientId);

//        if (updated) {
//            emailSenderService.getEmailMessage(timeslotDto);
//        }
        if(updated){
           return timeslotMapper.getPatientUpcomingAppointments(patientId);
        }
        return Collections.EMPTY_LIST;
    }

    public ResponseEntity<Boolean> deleteTimeslot(String timeslotId) {
        boolean deleted = timeslotMapper.deleteTimeslot(timeslotId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(deleted, status);
    }

//    TODO improve cancel appointment logic
    public ResponseEntity<Collection<AppointmentDTO>> cancelAppointment(TimeslotId timeslotId) {
        boolean cancelled = timeslotMapper.cancelAppointment(timeslotId.getTimeslotId());
        Collection<AppointmentDTO> upcomingAppointments = timeslotMapper
                .getPatientUpcomingAppointments(loggedUserService.getId());
        ResponseEntity.ok(upcomingAppointments);
        return ResponseEntity.ok(upcomingAppointments);
    }

}
