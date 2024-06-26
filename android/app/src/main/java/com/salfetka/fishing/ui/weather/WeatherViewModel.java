package com.salfetka.fishing.ui.weather;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openmeteo.sdk.Aggregation;
import com.openmeteo.sdk.Variable;
import com.openmeteo.sdk.VariableWithValues;
import com.openmeteo.sdk.VariablesSearch;
import com.openmeteo.sdk.VariablesWithTime;
import com.salfetka.fishing.models.weather.SunTimes;
import com.salfetka.fishing.models.weather.UnitMeasure;
import com.salfetka.fishing.models.weather.Weather;
import com.salfetka.fishing.models.weather.WeatherApiService;
import com.salfetka.fishing.models.weather.WeatherState;
import com.salfetka.fishing.models.weather.Wind;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.openmeteo.sdk.WeatherApiResponse;

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

    private static final String BASE_URL = "https://api.open-meteo.com/";

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

    private WeatherApiService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(WeatherApiService.class);
    }

    // Сюда пишешь код загрузки погоды с api
    public void updateWeather() {
        WeatherApiService service = getService();

        Call<ResponseBody> call = service.getWeatherForecast(
                52.0625,
                41.1875,
                "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,wind_speed_10m_max,wind_direction_10m_dominant",
                "auto",
                10,
                "ms",
                "flatbuffers"
        );
        // Загрузка прогноза погоды на ближайшие дни
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null){
                        try {
                            byte[] responseIn = response.body().bytes();
                            ByteBuffer buffer = ByteBuffer.wrap(responseIn).order(ByteOrder.LITTLE_ENDIAN);
                            WeatherApiResponse apiResponse = WeatherApiResponse.getRootAsWeatherApiResponse((ByteBuffer) buffer.position(4));
                            VariablesWithTime daily = apiResponse.daily();
                            VariableWithValues weatherCode = new VariablesSearch(daily)
                                    .variable(Variable.weather_code)
                                    .first();
                            VariableWithValues temperatureMax = new VariablesSearch(daily)
                                    .variable(Variable.temperature)
                                    .altitude(2)
                                    .aggregation(Aggregation.maximum)
                                    .first();
                            VariableWithValues temperatureMin = new VariablesSearch(daily)
                                    .variable(Variable.temperature)
                                    .altitude(2)
                                    .aggregation(Aggregation.minimum)
                                    .first();
                            VariableWithValues precipitationSum = new VariablesSearch(daily)
                                    .variable(Variable.precipitation)
                                    .aggregation(Aggregation.sum)
                                    .first();
                            VariableWithValues windOrientation = new VariablesSearch(daily)
                                    .variable(Variable.wind_direction)
                                    .first();
                            VariableWithValues windSpeed = new VariablesSearch(daily)
                                    .variable(Variable.wind_speed)
                                    .first();
                            Calendar now = new GregorianCalendar();
                            ArrayList<Weather> newDaysWeatherList = new ArrayList<>();
                            for (int i = 0; i < temperatureMin.valuesLength(); i++) {
                                newDaysWeatherList.add(new Weather(
                                        (Calendar) now.clone(),
                                        0,
                                        WeatherState.getWeatherState((int)weatherCode.values(i), false),
                                        Math.round(temperatureMax.values(i)),
                                        Math.round(temperatureMin.values(i)),
                                        0,
                                        precipitationSum.values(i),
                                        Wind.getWindDirection(windOrientation.values(i)),
                                        Math.round(windSpeed.values(i)),
                                        0,
                                        0));
                                now.add(Calendar.DAY_OF_YEAR, 1);
                            }
                            daysWeatherList.setValue(newDaysWeatherList);
                        } catch (IOException e) {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
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