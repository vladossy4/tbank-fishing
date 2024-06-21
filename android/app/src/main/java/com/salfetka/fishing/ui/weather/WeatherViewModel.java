package com.salfetka.fishing.ui.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salfetka.fishing.models.weather.SunTimes;
import com.salfetka.fishing.models.weather.UnitMeasure;
import com.salfetka.fishing.models.weather.Weather;
import com.salfetka.fishing.models.weather.WeatherState;
import com.salfetka.fishing.models.weather.Wind;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/** Управление данными о погоде */
public class WeatherViewModel extends ViewModel {
    /** Поле, хранящее единицы измерения */
    private final MutableLiveData<UnitMeasure> unitMeasure = new MutableLiveData<>();
    /** Поле, хранящее текущую погоду */
    private final MutableLiveData<Weather> weather = new MutableLiveData<>();
    /** Поле, хранящее сведения о восходе и заходе солнца */
    private final MutableLiveData<SunTimes> sunTimes = new MutableLiveData<>();
    /** Поле, хранящее погоду по часам */
    private final MutableLiveData<List<Weather>> hoursWeatherList = new MutableLiveData<>();
    /** Поле, хранящее погоду по дням */
    private final MutableLiveData<List<Weather>> daysWeatherList = new MutableLiveData<>();

    public WeatherViewModel() {
        //
        // Описание полей классов описал в javadoc. При наведении на конструктор будет информация о полях
        //
        // Это поле изменяешь если другие единицы измерения. Например скорость ветра в км/ч, а не м/с
        unitMeasure.setValue(new UnitMeasure("°C", "м/с", "мбар"));
        //
        // Это пример создания данных о погоде
        // Можешь это не редактировать
        // Загрузка погоды будет в методе updateWeather ниже
        //
        // Так создается погода на текущее время
        weather.setValue(new Weather(
                new GregorianCalendar(),
                23,
                WeatherState.PartlyCloudy, // Описание погоды в enum. Если api будет давать больше вариантов погоды,
                // чем имеется, то добавь новые значения, а значки я обновлю позже
                // (Просто используй данные о значках с предыдущих полей)
                27,
                15,
                10,
                0,
                Wind.NorthEast, // Направление ветра в enum. Поскольку количество вариантов ограничено, то сложностей не должно быть
                3,
                65,
                1012
        ));
        // Так задаются сведения о восходе и заходе солнца
        sunTimes.setValue(new SunTimes(
                "02:37",
                "03:35",
                "20:58",
                "21:56"
        ));
        // Так задаются сведения о погоде на ближайшие часы.
        // Сведения об минимальной и максимальной температуре за день игнорируются [поля maxTemperature и minTemperature]
        // (Можно написать туда что угодно, если api не дает информации)
        ArrayList<Weather> hourWeatherList = new ArrayList<>();
        hourWeatherList.add(weather.getValue());
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 13, 0), 23, WeatherState.PartlyCloudy, 27, 15, 10, 0, Wind.NorthEast, 3, 65, 1012));
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 14, 0), 24, WeatherState.Cloudy, 27, 15, 10, 0, Wind.East, 5, 55, 1011));
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 15, 0), 25, WeatherState.Sunny, 27, 15, 5, 0, Wind.SouthEast, 6, 50, 1005));
        hourWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18, 16, 0), 25, WeatherState.PartlyCloudyNight, 27, 15, 5, 0, Wind.South, 6, 50, 1015));
        hoursWeatherList.setValue(hourWeatherList);
        // Так задаются сведения о погоде на ближайшие дни.
        // Сведения об текущей температуре игнорируются [поле temperature] (Можно написать тудв что угодно, если api не дает информации)
        ArrayList<Weather> dayWeatherList = new ArrayList<>();
        dayWeatherList.add(weather.getValue());
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 18), 23, WeatherState.PartlyCloudy, 27, 15, 10, 0, Wind.NorthEast, 3, 65, 1012));
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 19), 24, WeatherState.Rainy, 23, 12, 80, 10, Wind.West, 7, 85, 1013));
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 20), 25, WeatherState.Thunderstorm, 25, 10, 70, 5, Wind.NorthWest, 10, 90, 1002));
        dayWeatherList.add(new Weather(new GregorianCalendar(2024, 5, 21), 25, WeatherState.Sunny, 30, 20, 5, 0, Wind.North, 6, 50, 1025));
        daysWeatherList.setValue(dayWeatherList);

        updateWeather();
    }

    // Сюда пишешь код загрузки погоды с api
    public void updateWeather() {
//        Загружаешь данные о погоде и после этого создаешь объекты ниже на основе полученных данных
//        Weather newWeather = ...
//        SunTimes newSunTimes = ...
//        ArrayList<Weather> newHoursWeatherList = ...
//        ArrayList<Weather> newDaysWeatherList = ...
        //Раскомментируешь, когда объекты будут созданы
//        weather.setValue(newWeather);
//        sunTimes.setValue(newSunTimes);
//        hoursWeatherList.setValue(newHoursWeatherList);
//        daysWeatherList.setValue(newDaysWeatherList);
    }
    // Конец справки
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
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
}