package com.sourcery.clinicapp.timeslot.controller;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.*;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping()
    public Timeslot getTimeslot(@RequestParam UUID timeslotId) {
        return timeslotService.getTimeslot(timeslotId);
    }

    @GetMapping("getAll")
    public Collection<Timeslot> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @GetMapping("getPhyTimeslots/{physicianId}")
    public ResponseEntity<List<TimeslotsDto>> getPhyTimeslots(@PathVariable UUID physicianId, @RequestParam Optional<String> date) {
        return timeslotService.getPhyTimeslots(physicianId, date.orElseGet(() -> LocalDate.now().toString()));
    }

    @GetMapping("getMonthsTimeslots/{physicianId}")
    public ResponseEntity<List<TimeslotByDate>> getMonthsTimeslots(
            @PathVariable UUID physicianId, @RequestParam Optional<String> date) {
        return timeslotService.getMonthsTimeslots(physicianId, date.orElseGet(() -> LocalDate.now().toString()));
    }

    @GetMapping("patientUpcomingAppointments/{id}")
    public Collection<AppointmentDTO> getPatientUpcomingAppointments(@PathVariable("id") UUID id) {
        return timeslotService.getPatientUpcomingAppointments(id);
    }

    @GetMapping("patientPastAppointments/{offset}")
    public Collection<AppointmentDTO> getPatientPastAppointments(@PathVariable("offset") int offset) {
        return timeslotService.getPatientPastAppointments(offset);
    }

    @GetMapping("patientPastAppointmentsAmount")
    public int getPastAppointmentAmount() {
        return timeslotService.getPastAppointmentAmount();
    }

    @PostMapping
    public boolean addTimeslot(@RequestBody TimeslotDto timeslotDto) {
        return timeslotService.addTimeslot(timeslotDto);
    }

    @PatchMapping("bookAppointment")
    public ResponseEntity<Timeslot> bookAppointment(@RequestBody TimeslotFullDto timeslotFullDto) {
        return timeslotService.bookAppointment(timeslotFullDto);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteTimeslot(@RequestParam UUID timeslotId) {
        return timeslotService.deleteTimeslot(timeslotId);
    }

    @PatchMapping("/cancelAppointment")
    public ResponseEntity<Boolean> cancelAppointment(@RequestParam UUID timeslotId) {
        return timeslotService.cancelAppointment(timeslotId);
    }

}
