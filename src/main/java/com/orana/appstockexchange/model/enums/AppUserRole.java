package com.orana.appstockexchange.model.enums;

public enum AppUserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    AppUserRole(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}