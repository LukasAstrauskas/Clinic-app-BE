package com.sourcery.clinicapp.timeslot.controller;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotDTO;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotId;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import com.sourcery.clinicapp.timeslot.model.dto.GroupedTimeslots;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping()
    public Timeslot getTimeslot(@RequestParam String timeslotId) {
        return timeslotService.getTimeslot(timeslotId);
    }

    @GetMapping("getAll")
    public Collection<Timeslot> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @GetMapping("getMonthsTimeslots/{physicianId}")
    public ResponseEntity<List<GroupedTimeslots>> getMonthsTimeslots(
            @PathVariable String physicianId, @RequestParam(required = false, defaultValue = "") String date) {
        return timeslotService.getMonthsTimeslots(physicianId, date);
    }

    @GetMapping("patientUpcomingAppointments")
    public Collection<AppointmentDTO> getPatientUpcomingAppointments() {
        return timeslotService.getPatientUpcomingAppointments();
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
    public Collection<AppointmentDTO> bookAppointment(@RequestBody Timeslot timeslot) {
        return timeslotService.bookAppointment(timeslot);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteTimeslot(@RequestParam String timeslotId) {
        return timeslotService.deleteTimeslot(timeslotId);
    }

    //    patient cancel appointment
    @PatchMapping("/cancelAppointment")
    public ResponseEntity<String> patientCancelAppointment(@RequestBody TimeslotId timeslotId) {
        return timeslotService.cancelAppointment(timeslotId);
    }

}
