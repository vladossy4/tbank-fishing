package com.salfetka.fishing.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salfetka.fishing.models.SunTimes;
import com.salfetka.fishing.models.Weather;
import com.salfetka.fishing.models.Wind;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<Weather> weather;
    private final MutableLiveData<SunTimes> sunTimes;
    public WeatherViewModel() {
        weather = new MutableLiveData<>(new Weather(
                new Date(),
                "°C",
                "м/с",
                "мбар",
                23,
                "Переменная\nоблачность",
                27,
                15,
                10,
                Wind.NorthEast,
                3,
                65,
                1012
        ));
        sunTimes = new MutableLiveData<>(new SunTimes(
                "02:37",
                "03:35",
                "20:58",
                "21:56"
        ));

    }

    public MutableLiveData<Weather> getWeather() {
        return weather;
    }
    public MutableLiveData<SunTimes> getSunTimes() {
        return sunTimes;
    }

    public void setWeather(Weather newWeather) {
        weather.setValue(newWeather);
    }
}