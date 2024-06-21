package com.salfetka.fishing.models.weather;

/** Хранит данные о восходе и заходе солнца */
public class SunTimes {
    final String dawn;
    final String sunrise;
    final String sunset;
    final String twilight;

    /** Создание данных о восходе и заходе солнца
     * @param dawn время рассвета (становится светло, но солнце ещё не взошло)
     * @param sunrise время восхода солнца
     * @param sunset время захода солнца
     * @param twilight время сумерек (ещё не стемнело, но солнце уже зашло) */
    public SunTimes(String dawn, String sunrise, String sunset, String twilight) {
        this.dawn = dawn;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.twilight = twilight;
    }

    /** Получение времени рассвета (становится светло, но солнце ещё не взошло) */
    public String getDawn() {
        return dawn;
    }
    /** Получение времени восхода солнца */
    public String getSunrise() {
        return sunrise;
    }
    /** Получение времени захода солнца */
    public String getSunset() {
        return sunset;
    }
    /** Получение времени сумерек (ещё не стемнело, но солнце уже зашло) */
    public String getTwilight() {
        return twilight;
    }
}
