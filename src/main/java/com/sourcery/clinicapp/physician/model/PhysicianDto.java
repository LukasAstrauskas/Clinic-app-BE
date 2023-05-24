package com.sourcery.clinicapp.physician.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class PhysicianDto {
    private String name;
    private String email;
    private String password;
    private UUID occupationId;
}