package com.sourcery.clinicapp.timeslot.controller;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.TimeslotDTO;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotList;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
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

    @GetMapping("getMonthsTimeslots/{physicianId}")
    public ResponseEntity<List<TimeslotList>> getMonthsTimeslots(
            @PathVariable UUID physicianId, @RequestParam(required = false, defaultValue = "") String date) {
        return timeslotService.getMonthsTimeslots(physicianId, date);
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
    public boolean addTimeslot(@RequestBody TimeslotDTO timeslotDto) {
        return timeslotService.addTimeslot(timeslotDto);
    }

    @PatchMapping("bookAppointment")
    public ResponseEntity<Boolean> bookAppointment(@RequestBody TimeslotDTO timeslotDTO) {
        return timeslotService.bookAppointment(timeslotDTO);
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
