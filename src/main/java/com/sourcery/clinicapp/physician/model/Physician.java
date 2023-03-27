package com.sourcery.clinicapp.physician.model;

import com.sourcery.clinicapp.occupation.model.Occupation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Physician {
    private UUID id;
    private String name;
    private String email;
    private Occupation occupation;
}
