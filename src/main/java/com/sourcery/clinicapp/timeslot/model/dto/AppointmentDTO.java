package com.sourcery.clinicapp.timeslot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String id;
    private String name;
    private String surname;
    private LocalDateTime date;
    private String occupation;
}
