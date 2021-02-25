package com.mnyshenko.taxiSpringApp.constant;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return this.name().replace("ROLE_", "");
    }
}
