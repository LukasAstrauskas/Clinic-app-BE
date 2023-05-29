package com.sourcery.clinicapp.user.model;


public enum Type {
    ADMIN("admin"),
    PHYSICIAN("physician"),
    PATIENT("patient");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
