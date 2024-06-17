package com.salfetka.fishing.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
    /** Время, на которое был выполнен прогноз погоды */
    final Date weatherTime;
    /** Единицы измерения температуры */
    final String temperatureUnit;
    /** Единицы измерения скорости */
    final String speedUnit;
    /** Единицы измерения давления */
    final String pressureUnit;
    /** Температура воздуха  */
    final Integer temperature;
    /** Состояние погоды */
    final String currentWeather;
    /** Максимальная температура */
    final Integer maxTemperature;
    /** Минимальная температура */
    final Integer minTemperature;
    /** Вероятность осадков */
    final int chanceOfPrecipitation;
    /** Направление ветра */
    final Wind windOrientation;
    /** Скорость ветра */
    final int windSpeed;
    /** Влажность воздуха */
    final int humidity;
    /** Атмосферное давление */
    final int pressure;

    public Weather(Date weatherTime, String temperatureUnit, String speedUnit, String pressureUnit, int temperature, String currentWeather, int maxTemperature, int minTemperature, int chanceOfPrecipitation, Wind windOrientation, int windSpeed, int humidity, int pressure){
        this.weatherTime = weatherTime;
        this.temperatureUnit = temperatureUnit;
        this.speedUnit = speedUnit;
        this.pressureUnit = pressureUnit;
        this.temperature = temperature;
        this.currentWeather = currentWeather;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.chanceOfPrecipitation = chanceOfPrecipitation;
        this.windOrientation = windOrientation;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public Date getWeatherTime() {
        return weatherTime;
    }

    public String getLastUpdateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM k:mm", Locale.getDefault());
        return formatter.format(weatherTime);
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

    public Integer getTemperature() {
        return temperature;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public int getChanceOfPrecipitation() {
        return chanceOfPrecipitation;
    }

    public Wind getWindOrientation() {
        return windOrientation;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }
}
