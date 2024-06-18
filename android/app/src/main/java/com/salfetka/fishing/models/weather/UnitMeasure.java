package com.salfetka.fishing.models.weather;

public class UnitMeasure {
    /** Единицы измерения температуры */
    final String temperatureUnit;
    /** Единицы измерения скорости */
    final String speedUnit;
    /** Единицы измерения давления */
    final String pressureUnit;

    public UnitMeasure(String temperatureUnit, String speedUnit, String pressureUnit) {
        this.temperatureUnit = temperatureUnit;
        this.speedUnit = speedUnit;
        this.pressureUnit = pressureUnit;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getSpeedUnit() {
        return speedUnit;
    }

    public String getPressureUnit() {
        return pressureUnit;
    }
}
