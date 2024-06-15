package com.salfetka.fishing.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<String> day = new MutableLiveData<>();
    private final MutableLiveData<String> mainTemperature = new MutableLiveData<>();
    private final MutableLiveData<String> mainWeather = new MutableLiveData<>();

    public WeatherViewModel() {
        day.setValue("Сейчас");
        mainTemperature.setValue("23°C");
        mainWeather.setValue("Переменная\nоблачность");
    }

    public LiveData<String> getTextDay() {
        return day;
    }
    public LiveData<String> getMainTemperature() {
        return mainTemperature;
    }
    public LiveData<String> getMainWeather() {
        return mainWeather;
    }
}