package com.sourcery.clinicapp.security.model;

public enum User {
    PATIENT("SCOPE_PATIENT"),
    PHYSICIAN("SCOPE_PHYSICIAN"),
    ADMIN("SCOPE_ADMIN");

    private final String authority;

    private User(String authority) {
        this.authority = authority;
    }

    public String authority() {
        return authority;
    }
}
