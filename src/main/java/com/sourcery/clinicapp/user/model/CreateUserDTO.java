package com.sourcery.clinicapp.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String type;
    private UUID infoID;
}
