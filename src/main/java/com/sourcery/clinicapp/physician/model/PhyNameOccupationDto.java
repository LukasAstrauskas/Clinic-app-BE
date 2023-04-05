package com.sourcery.clinicapp.physician.model;

import java.util.UUID;

public record PhyNameOccupationDto(UUID physicianId, String name, String occupation) {

}
