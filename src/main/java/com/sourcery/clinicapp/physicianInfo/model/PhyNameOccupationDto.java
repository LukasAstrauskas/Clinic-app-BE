package com.sourcery.clinicapp.physicianInfo.model;

import java.util.UUID;

public record PhyNameOccupationDto(UUID physicianId, String name, String occupation) {

}
