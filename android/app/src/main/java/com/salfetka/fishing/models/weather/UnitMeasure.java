package com.salfetka.fishing.models.weather;

/** Хранит настройки единиц измерений */
public class UnitMeasure {
    /** Единицы измерения температуры */
    final String temperatureUnit;
    /** Единицы измерения скорости */
    final String speedUnit;
    /** Единицы измерения давления */
    final String pressureUnit;

    /** Создание указания единиц измерения
     * @param temperatureUnit Единицы измерения температуры
     * @param speedUnit Единицы измерения скорости
     * @param pressureUnit Единицы измерения давления */
    public UnitMeasure(String temperatureUnit, String speedUnit, String pressureUnit) {
        this.temperatureUnit = temperatureUnit;
        this.speedUnit = speedUnit;
        this.pressureUnit = pressureUnit;
    }
    /** Получение единицы измерения температуры */
    public String getTemperatureUnit() {
        return temperatureUnit;
    }
    /** Получение единицы измерения скорости */
    public String getSpeedUnit() {
        return speedUnit;
    }
    /** Получение единицы измерения давления */
    public String getPressureUnit() {
        return pressureUnit;
    }
}
