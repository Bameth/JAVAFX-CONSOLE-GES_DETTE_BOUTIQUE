package org.example.data.enums;

public enum EtatDette {
    SOLDEES, NONSOLDEES,ARCHIVER;

    public static EtatDette getValue(String value) {
        for (EtatDette t : EtatDette.values()) {
            if (t.name().compareToIgnoreCase(value) == 0) {
                return t;
            }
        }
        return null;
    }
}
