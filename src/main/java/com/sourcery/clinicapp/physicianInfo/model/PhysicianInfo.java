package com.sourcery.clinicapp.physicianInfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhysicianInfo {
    private UUID userId;
    private UUID occupationId;

}
