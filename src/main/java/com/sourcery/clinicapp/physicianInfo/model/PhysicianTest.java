package com.sourcery.clinicapp.physicianInfo.model;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhysicianTest {

    private UUID phyID;
    private String phyName;
    private String phyEmail;

}
