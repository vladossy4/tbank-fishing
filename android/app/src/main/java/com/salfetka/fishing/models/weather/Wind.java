package com.salfetka.fishing.models.weather;

/** Описывает направление ветра */
public enum Wind {
    North("Северный", "С"),
    NorthEast("Северо-восточный", "СВ"),
    East("Восточный", "В"),
    SouthEast("Юго-восточный", "ЮВ"),
    South("Южный", "Ю"),
    SouthWest("Юго-западный", "ЮЗ"),
    West("Западный", "З"),
    NorthWest("Северо-западный", "СЗ");

    private final String fullName;
    private final String shortName;

    Wind(String fullName, String shortName){
        this.fullName = fullName;
        this.shortName = shortName;
    }

    /** Полное название направления ветра */
    public String getFullName() {
        return fullName;
    }
    /** Сокращенное название направления ветра */
    public String getShortName() {
        return shortName;
    }
    /** Направление ветра в градусах для поворота значка указателя */
    public int getAngle() {
        return this.ordinal() * 45;
    }
}
