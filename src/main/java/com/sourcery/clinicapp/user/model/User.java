package com.sourcery.clinicapp.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String type;
    private String occupationId;
}
