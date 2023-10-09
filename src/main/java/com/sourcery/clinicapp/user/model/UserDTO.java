package com.sourcery.clinicapp.user.model;

import com.sourcery.clinicapp.occupation.model.Occupation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String type;
//    private String occupation;
    private Occupation occupation;
}