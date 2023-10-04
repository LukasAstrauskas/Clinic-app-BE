package com.sourcery.clinicapp.login.model;

import com.sourcery.clinicapp.loggedUser.model.LoggedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginDto {
    private LoggedUser loggedUser;
    private String token;
}

