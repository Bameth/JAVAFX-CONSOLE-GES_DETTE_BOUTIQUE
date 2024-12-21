package org.example.data.enums;

public enum TypeEtat {
    ACTIVER,DESACTIVER;
    public static TypeEtat getValue(String value) {
        for (TypeEtat t : TypeEtat.values()) {
            if (t.name().compareToIgnoreCase(value) == 0) {
                return t;
            }
        }
        return null;
    }
}
