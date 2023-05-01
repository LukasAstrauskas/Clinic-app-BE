package com.sourcery.clinicapp.timeslot.controller;

import com.sourcery.clinicapp.timeslot.model.dto.TimeslotDto;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotFullDto;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotsDto;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import com.sourcery.clinicapp.utils.DateTimeHelper;
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

    @GetMapping("/{physicianId}/{date}/{time}")
    public Timeslot getTimeslot(@PathVariable UUID physicianId, @PathVariable String date, @PathVariable String time) {
        return timeslotService.getTimeslot(physicianId, DateTimeHelper.toDateTime(date, time)).orElseGet(Timeslot::new);
    }

    @GetMapping("getAll")
    public Collection<Timeslot> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @GetMapping("getPhyTimeslots/{physicianId}")
    public List<TimeslotsDto> getPhyTimeslots(@PathVariable UUID physicianId) {
        return timeslotService.getPhyTimeslots(physicianId);
    }

    @PostMapping()
    public boolean addTimeslot(@RequestBody TimeslotDto timeslotDto) {
        return timeslotService.addTimeslot(timeslotDto);
    }
    @GetMapping("/count/{physicianId}/{patientId}")
    public Long countUpcomingTimeslotsWithPhysician(@PathVariable UUID physicianId, @PathVariable UUID patientId) {
        return timeslotService.countUpcomingTimeslotsWithPhysician(physicianId, patientId);
    }

    @PatchMapping()
    public ResponseEntity<Timeslot> updateTimeslot(@RequestBody TimeslotFullDto timeslotFullDto) {
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
