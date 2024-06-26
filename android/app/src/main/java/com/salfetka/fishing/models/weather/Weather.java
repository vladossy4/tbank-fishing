package com.salfetka.fishing.models.weather;

import com.salfetka.fishing.models.DateFormatter;

import java.util.Calendar;

/** Хранит данные о погоде */
public class Weather {
    /** Время, на которое был выполнен прогноз погоды */
    final Calendar weatherTime;
    /** Температура воздуха  */
    final int temperature;
    /** Состояние погоды */
    final WeatherState currentWeather;
    /** Максимальная температура */
    final int maxTemperature;
    /** Минимальная температура */
    final int minTemperature;
    /** Вероятность осадков */
    final int chanceOfPrecipitation;
    /** Количество осадков */
    final float amountOfPrecipitation;
    /** Направление ветра */
    final Wind windOrientation;
    /** Скорость ветра */
    final int windSpeed;
    /** Влажность воздуха */
    final int humidity;
    /** Атмосферное давление */
    final int pressure;

    /** Создание данных о состоянии погоды
     * @param weatherTime Время, на которое был выполнен прогноз погоды
     * @param temperature Температура воздуха
     * @param currentWeather Состояние погоды
     * @param maxTemperature Максимальная температура
     * @param minTemperature Минимальная температура
     * @param chanceOfPrecipitation Вероятность осадков
     * @param amountOfPrecipitation Количество осадков
     * @param windOrientation Направление ветра
     * @param windSpeed Скорость ветра
     * @param humidity Влажность воздуха
     * @param pressure Атмосферное давление */
    public Weather(Calendar weatherTime, int temperature, WeatherState currentWeather, int maxTemperature, int minTemperature, int chanceOfPrecipitation, float amountOfPrecipitation, Wind windOrientation, int windSpeed, int humidity, int pressure){
        this.weatherTime = weatherTime;
        this.temperature = temperature;
        this.currentWeather = currentWeather;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.chanceOfPrecipitation = chanceOfPrecipitation;
        this.amountOfPrecipitation = amountOfPrecipitation;
        this.windOrientation = windOrientation;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public Calendar getWeatherTime() {
        return weatherTime;
    }

    public String getWeatherDateTimeFormat() {
        return DateFormatter.format(weatherTime, "d MMM k:mm");
    }

    public String getWeatherHourFormat() {
        return DateFormatter.format(weatherTime, "k:00");
    }

    public String getWeatherDayFormat() {
        return DateFormatter.format(weatherTime, "E\nd");
    }

    public int getTemperature() {
        return temperature;
    }

    public WeatherState getCurrentWeather() {
        return currentWeather;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getChanceOfPrecipitation() {
        return chanceOfPrecipitation;
    }

    public float getAmountOfPrecipitation() {
        return amountOfPrecipitation;
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
