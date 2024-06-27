package com.salfetka.fishing.models.weather;

import com.salfetka.fishing.models.DateFormatter;

import java.util.Calendar;

/** Хранит дополнительные данные о погоде */
public class OtherWeather {
    final Calendar sunrise;
    final Calendar sunset;
    /** Влажность воздуха */
    final int humidity;
    /** Атмосферное давление */
    final int pressure;
    /** Порывы ветра */
    final int windGust;
    /** Точка росы */
    final int dewPoint;

    /** Создание дополнительных данных о погоде
     * @param sunrise время восхода солнца
     * @param sunset время захода солнца
     * @param humidity влажность воздуха
     * @param pressure атмосферное давление
     * @param windGust порывы ветра
     * @param dewPoint точка росы */
    public OtherWeather(Calendar sunrise, Calendar sunset, int humidity, int pressure, int windGust, int dewPoint) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windGust = windGust;
        this.dewPoint = dewPoint;
    }

    /** Получение времени восхода солнца */
    public String getSunriseString() {
        return DateFormatter.format(sunrise, "HH:mm");
    }
    /** Получение времени захода солнца */
    public String getSunsetString() {
        return DateFormatter.format(sunset, "HH:mm");
    }
    /** Получение времени восхода солнца */
    public Calendar getSunrise() {
        return sunrise;
    }
    /** Получение времени захода солнца */
    public Calendar getSunset() {
        return sunset;
    }

    /** Получение влажности воздуха */
    public int getHumidity() {
        return humidity;
    }
    /** Получение атмосферного давления */
    public int getPressure() {
        return pressure;
    }
    /** Получение порывов ветра */
    public int getWindGust() {
        return windGust;
    }
    /** Получение точки росы */
    public int getDewPoint() {
        return dewPoint;
    }
}
