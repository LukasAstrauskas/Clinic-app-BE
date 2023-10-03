package com.sourcery.clinicapp.security.model;

public enum UserRole {
    PATIENT("SCOPE_PATIENT"),
    PHYSICIAN("SCOPE_PHYSICIAN"),
    ADMIN("SCOPE_ADMIN");

    private final String authority;

    private UserRole(String authority) {
        this.authority = authority;
    }

    public String value() {
        return authority;
    }
}
