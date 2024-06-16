package com.salfetka.fishing.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
    final Date lastUpdate;
    final String unitMeasure;
    final int currentTemperature;
    final String currentWeather;
    final int maxTemperature;
    final int minTemperature;

    public Weather(String unitMeasure, int currentTemperature, String currentWeather, int maxTemperature, int minTemperature){
        this.unitMeasure = unitMeasure;
        this.currentTemperature = currentTemperature;
        this.currentWeather = currentWeather;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        lastUpdate = new Date();
    }

    public String getLastUpdate() {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM k:mm", Locale.getDefault());
        return formatter.format(lastUpdate);
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public String addUnitMeasure(int value) {
        return value + unitMeasure;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }
}
