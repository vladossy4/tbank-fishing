package com.salfetka.fishing.models.weather;

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

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }
    public int getAngle() {
        return this.ordinal() * 45;
    }
}
