package com.sourcery.clinicapp.physician.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhysicianDto {
    private String name;
    private String email;
    private String password;
    private UUID occupationId;
}