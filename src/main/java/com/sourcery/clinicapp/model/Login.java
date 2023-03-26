package com.sourcery.clinicapp.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Login {

    private String email;

    private String password;

}
