package com.salfetka.fishing.models.weather;

public class SunTimes {
    final String dawn;
    final String sunrise;
    final String sunset;
    final String twilight;

    public SunTimes(String dawn, String sunrise, String sunset, String twilight) {
        this.dawn = dawn;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.twilight = twilight;
    }

    public String getDawn() {
        return dawn;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getTwilight() {
        return twilight;
    }
}
