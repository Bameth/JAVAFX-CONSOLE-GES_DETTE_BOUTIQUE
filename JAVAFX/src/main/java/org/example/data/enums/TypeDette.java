package org.example.data.enums;

public enum TypeDette {
    ENCOURS, ANNULER, ACCEPTER;

    public static TypeDette getValue(String value) {
        for (TypeDette t : TypeDette.values()) {
            if (t.name().compareToIgnoreCase(value) == 0) {
                return t;
            }
        }
        return null;
    }
}
