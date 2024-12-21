package org.example.data.enums;

public enum Role {
    ADMIN, BOUTIQUIER,CLIENT;

    public static Role getValue(String value) {
        for (Role r : Role.values()) {
            if (r.name().compareToIgnoreCase(value) == 0) {
                return r;
            }
        }
        return null;
    }
}
