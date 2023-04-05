package com.sourcery.clinicapp.timeslot.controller;

import com.sourcery.clinicapp.timeslot.model.dto.NewTimeslotDto;
import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.TimePatientDto;
import com.sourcery.clinicapp.timeslot.model.dto.TimeslotsDto;
import com.sourcery.clinicapp.timeslot.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("timeslot")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping("getAll")
    public Collection<Timeslot> getAllTimeslots() {
        return timeslotService.getAllTimeslots();
    }

    @GetMapping("getPhyTimeslots/{physicianId}")
    public List<TimeslotsDto> getPhyTimeslots(@PathVariable UUID physicianId) {
        return timeslotService.getPhyTimeslots(physicianId);
    }

    @PostMapping()
    public boolean addTimeslot(@RequestBody NewTimeslotDto newTimeslotDto) {
        return timeslotService.addTimeslot(newTimeslotDto);
    }

    @GetMapping("/{physicianId}/{dateTime}")
    public Timeslot getTimeslot(@PathVariable UUID physicianId, @PathVariable LocalDateTime dateTime) {
        return timeslotService.getTimeslot(physicianId, dateTime);
    }

    @DeleteMapping("/{physicianId}/{dateTime}")
    public boolean deleteTimeslot(@PathVariable UUID physicianId, @PathVariable LocalDateTime dateTime) {
        return timeslotService.deleteTimeslot(physicianId, dateTime);
    }
}
