package com.sourcery.clinicapp.timeslot.controller;

import com.sourcery.clinicapp.notifications.EmailSenderService;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotDto;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotFullDto;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotsDto;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import com.sourcery.clinicapp.user.service.UserService;
import com.sourcery.clinicapp.utils.DateTimeHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    @GetMapping("/{physicianId}/{date}/{time}")
    public Timeslot getTimeslot(@PathVariable UUID physicianId, @PathVariable String date, @PathVariable String time) {
        return timeslotService.getTimeslot(physicianId, DateTimeHelper.toDateTime(date, time)).orElseGet(Timeslot::new);
    }

    @GetMapping("getAll")
    public Collection<Timeslot> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @GetMapping("getPhyTimeslots/{physicianId}")
    public ResponseEntity<List<TimeslotsDto>> getPhyTimeslots(@PathVariable UUID physicianId, @RequestParam Optional<String> date) {
        return timeslotService.getPhyTimeslots(physicianId, date.orElseGet(() -> LocalDate.now().toString()));
    }

    @PostMapping()
    public boolean addTimeslot(@RequestBody TimeslotDto timeslotDto) {
        return timeslotService.addTimeslot(timeslotDto);
    }
    
    @PatchMapping()
    public ResponseEntity<Timeslot> updateTimeslot(@RequestBody TimeslotFullDto timeslotFullDto) {

        String toEmail = userService.getAUserById(timeslotFullDto.patientId()).getEmail();
        String emailSubject = "Appointment confirmation " + LocalDate.now();
        String physicianName = "\nPhysician name: " + userService.getAUserById(timeslotFullDto.physicianId()).getName();
        String patientName = "Hello, " + userService.getAUserById(timeslotFullDto.patientId()).getName() +",\n";
        String appointmentDate = "\nAppointment date: " + timeslotFullDto.date() + ", " + timeslotFullDto.time();
        String emailMessage = patientName + "\nYour appointment successfully confirmed!\n" + physicianName + ";" + appointmentDate + ";\nIf you have any questions, please contact +37065468789; \nIf you want to cancel appointment, please login http://localhost:3000/login;";

        emailSenderService.sendEmail(toEmail, emailSubject, emailMessage);

        return timeslotService.updateTimeslot(timeslotFullDto);
    }

    @DeleteMapping()
    public ResponseEntity<Timeslot> deleteTimeslot(@RequestBody TimeslotDto timeslotDto) {
        return timeslotService.deleteTimeslot(timeslotDto);
    }

    @PatchMapping("/{physicianId}/{patientId}")
    public ResponseEntity<Void> removePatientFromTimeslot(@PathVariable UUID physicianId, @PathVariable UUID patientId) {
        return timeslotService.removePatientFromTimeslot(physicianId, patientId);
    }

}
