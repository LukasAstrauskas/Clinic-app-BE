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
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String type;
}
