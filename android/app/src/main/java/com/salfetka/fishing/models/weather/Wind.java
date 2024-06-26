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

    public static Wind getWindDirection(float degrees) {
        if (degrees < 0 || degrees > 360) {
            throw new IllegalArgumentException("Degrees must be in the range [0, 360)");
        }

        if (degrees >= 337.5 || degrees < 22.5) {
            return Wind.North;
        } else if (degrees >= 22.5 && degrees < 67.5) {
            return Wind.NorthEast;
        } else if (degrees >= 67.5 && degrees < 112.5) {
            return Wind.East;
        } else if (degrees >= 112.5 && degrees < 157.5) {
            return Wind.SouthEast;
        } else if (degrees >= 157.5 && degrees < 202.5) {
            return Wind.South;
        } else if (degrees >= 202.5 && degrees < 247.5) {
            return Wind.SouthEast;
        } else if (degrees >= 247.5 && degrees < 292.5) {
            return Wind.West;
        } else {
            return Wind.NorthWest;
        }
    }
}
