package com.salfetka.fishing.ui.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salfetka.fishing.models.weather.SunTimes;
import com.salfetka.fishing.models.weather.UnitMeasure;
import com.salfetka.fishing.models.weather.Weather;
import com.salfetka.fishing.models.weather.WeatherState;
import com.salfetka.fishing.models.weather.Wind;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<UnitMeasure> unitMeasure;
    private final MutableLiveData<Weather> weather;
    private final MutableLiveData<SunTimes> sunTimes;
    private final MutableLiveData<List<Weather>> hoursWeatherList;
    private final MutableLiveData<List<Weather>> daysWeatherList;
    public WeatherViewModel() {
        unitMeasure = new MutableLiveData<>(new UnitMeasure("°C", "м/с", "мбар"));
        weather = new MutableLiveData<>(new Weather(
                new GregorianCalendar(),
                23,
                WeatherState.PartlyCloudy,
                27,
                15,
                10,
                0,
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
        ArrayList<Weather> hourWeatherList = new ArrayList<>();
        hourWeatherList.add(weather.getValue());
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 13, 0), 23, WeatherState.PartlyCloudy, 27, 15, 10, 0, Wind.NorthEast, 3, 65, 1012));
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 14, 0), 24, WeatherState.Cloudy, 27, 15, 10, 0, Wind.East, 5, 55, 1011));
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 15, 0), 25, WeatherState.Sunny, 27, 15, 5, 0, Wind.SouthEast, 6, 50, 1005));
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 16, 0), 25, WeatherState.PartlyCloudyNight, 27, 15, 5, 0, Wind.South, 6, 50, 1015));

        ArrayList<Weather> dayWeatherList = new ArrayList<>();
        dayWeatherList.add(weather.getValue());
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18), 23, WeatherState.PartlyCloudy, 27, 15, 10, 0, Wind.NorthEast, 3, 65, 1012));
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 19), 24, WeatherState.Rainy, 23, 12, 80, 10, Wind.West, 7, 85, 1013));
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 20), 25, WeatherState.Thunderstorm, 25, 10, 70, 5, Wind.NorthWest, 10, 90, 1002));
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 21), 25, WeatherState.Sunny, 30, 20, 5, 0, Wind.North, 6, 50, 1025));
        hoursWeatherList = new MutableLiveData<>(hourWeatherList);
        daysWeatherList = new MutableLiveData<>(dayWeatherList);
    }

    public MutableLiveData<Weather> getWeather() {
        return weather;
    }

    public MutableLiveData<UnitMeasure> getUnitMeasure() {
        return unitMeasure;
    }

    public MutableLiveData<SunTimes> getSunTimes() {
        return sunTimes;
    }

    public MutableLiveData<List<Weather>> getHoursWeatherList() {
        return hoursWeatherList;
    }
    public MutableLiveData<List<Weather>> getDaysWeatherList() {
        return daysWeatherList;
    }

    public void setWeather(Weather newWeather) {
        weather.setValue(newWeather);
    }
}