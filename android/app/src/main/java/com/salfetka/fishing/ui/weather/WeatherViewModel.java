package com.salfetka.fishing.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salfetka.fishing.models.Weather;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<Weather> weather;

    public WeatherViewModel() {
        weather = new MutableLiveData<>(new Weather(
                "°C",
                23,
                "Переменная\nоблачность",
                27,
                15));
    }

    public MutableLiveData<Weather> getWeather() {
        return weather;
    }

    public void setWeather(Weather newWeather) {
        weather.setValue(newWeather);
    }
}