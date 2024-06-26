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
    /** Адрес сайта для загрузки прогноза погоды */
    private static final String BASE_URL = "https://api.open-meteo.com/";
    /** Retrofit сервис для загрузки данных о погоде */
    private final WeatherApiService service = getService();
    /** Географическая широта */
    private double latitude;
    /** Географическая долгота */
    private double longitude;
    public WeatherViewModel() {
        unitMeasure.setValue(new UnitMeasure("°C", "м/с", "мбар"));
        sunTimes.setValue(new SunTimes(
                "02:37",
                "03:35",
                "20:58",
                "21:56"
        ));
        updateWeather();
    }

    private WeatherApiService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(WeatherApiService.class);
    }

    public void updateWeather() {
        // Загрузка координат местности
        latitude = 52.063;
        longitude = 41.1678;
        updateCurrentWeather();
        updateHoursWeatherList();
        updateDaysWeatherList();
    }

    private void updateCurrentWeather() {
        // Подготавливаем запрос для загрузки текущего прогноза погоды
        Call<ResponseBody> currentWeather = service.getWeatherCurrentForecast(
                latitude,
                longitude,
                "temperature_2m,relative_humidity_2m,precipitation,weather_code,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m",
                "dew_point_2m,precipitation_probability",
                "temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_probability_max",
                "auto",
                1,
                "ms",
                "flatbuffers"
        );
        // Загрузка текущего прогноза погоды
        currentWeather.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try (ResponseBody body = response.body()){
                        if (body != null) {
                            try {
                                byte[] responseIn = body.bytes();
                                ByteBuffer buffer = ByteBuffer.wrap(responseIn).order(ByteOrder.LITTLE_ENDIAN);
                                WeatherApiResponse apiResponse = WeatherApiResponse.getRootAsWeatherApiResponse((ByteBuffer) buffer.position(4));
                                VariablesWithTime current = apiResponse.current();
                                VariablesWithTime hourly = apiResponse.hourly();
                                VariablesWithTime daily = apiResponse.daily();
                                Weather currentWeather = new Weather(
                                        new GregorianCalendar(),
                                        Math.round(new VariablesSearch(current).variable(Variable.temperature).first().value()),
                                        WeatherState.getWeatherState((int)new VariablesSearch(current).variable(Variable.weather_code).first().value(), true),
                                        Math.round(new VariablesSearch(daily)
                                                .variable(Variable.temperature).aggregation(Aggregation.maximum)
                                                .first().values(0)),
                                        Math.round(new VariablesSearch(daily)
                                                .variable(Variable.temperature).aggregation(Aggregation.minimum)
                                                .first().values(0)),
                                        Math.round(new VariablesSearch(hourly)
                                                .variable(Variable.precipitation_probability).first().values(0)),
                                        new VariablesSearch(current).variable(Variable.precipitation).first().value(),
                                        Wind.getWindDirection(new VariablesSearch(current).variable(Variable.wind_direction).first().value()),
                                        Math.round(new VariablesSearch(current).variable(Variable.wind_speed).first().value()),
                                        Math.round(new VariablesSearch(current).variable(Variable.relative_humidity).first().value()),
                                        Math.round(new VariablesSearch(current).variable(Variable.surface_pressure).first().value())
                                );
                                weather.setValue(currentWeather);
                                buffer.clear();
                            } catch (IOException e) {

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private ArrayList<Weather> processingWeatherList(VariablesWithTime weather, int stepTime, boolean isHourly) {
        VariableWithValues weatherCode = new VariablesSearch(weather)
                .variable(Variable.weather_code).first();
        VariableWithValues windOrientation = new VariablesSearch(weather)
                .variable(Variable.wind_direction).first();
        VariableWithValues windSpeed = new VariablesSearch(weather)
                .variable(Variable.wind_speed).first();
        VariableWithValues temperature = null;
        VariableWithValues temperatureMax = null;
        VariableWithValues temperatureMin = null;
        VariableWithValues precipitationSum;
        if (isHourly) {
            temperature = new VariablesSearch(weather)
                    .variable(Variable.temperature)
                    .altitude(2).first();
            precipitationSum = new VariablesSearch(weather)
                    .variable(Variable.precipitation).first();
        }
        else {
            temperatureMax = new VariablesSearch(weather)
                    .variable(Variable.temperature).altitude(2)
                    .aggregation(Aggregation.maximum).first();
            temperatureMin = new VariablesSearch(weather)
                    .variable(Variable.temperature).altitude(2)
                    .aggregation(Aggregation.minimum).first();
            precipitationSum = new VariablesSearch(weather)
                    .variable(Variable.precipitation)
                    .aggregation(Aggregation.sum).first();
        }
        Calendar now = new GregorianCalendar();
        ArrayList<Weather> newWeatherList = new ArrayList<>();
        for (int i = 0; i < weatherCode.valuesLength(); i++) {
            //boolean isDay = (isHourly)? now. : true;
            newWeatherList.add(new Weather(
                    (Calendar) now.clone(),
                    (isHourly)? Math.round(temperature.values(i)) : 0,
                    WeatherState.getWeatherState((int)weatherCode.values(i), true),
                    (!isHourly)? Math.round(temperatureMax.values(i)) : 0,
                    (!isHourly)? Math.round(temperatureMin.values(i)) : 0,
                    0,
                    precipitationSum.values(i),
                    Wind.getWindDirection(windOrientation.values(i)),
                    Math.round(windSpeed.values(i)),
                    0,
                    0));
            now.add(stepTime, 1);
        }
        return newWeatherList;
    }

    private void updateHoursWeatherList() {
        // Подготавливаем запрос для загрузки прогноза погоды на 72 часа
        Call<ResponseBody> weatherHours = service.getWeatherHoursForecast(
                latitude,
                longitude,
                "temperature_2m,is_day,precipitation,weather_code,wind_speed_10m,wind_direction_10m",
                "auto",
                3,
                "ms",
                "flatbuffers"
        );
        // Загрузка прогноза погоды на ближайшие часы
        weatherHours.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try (ResponseBody body = response.body()){
                        if (body != null) {
                            try {
                                byte[] responseIn = body.bytes();
                                ByteBuffer buffer = ByteBuffer.wrap(responseIn).order(ByteOrder.LITTLE_ENDIAN);
                                WeatherApiResponse apiResponse = WeatherApiResponse.getRootAsWeatherApiResponse((ByteBuffer) buffer.position(4));
                                VariablesWithTime hourly = apiResponse.hourly();
                                ArrayList<Weather> newHoursWeatherList = processingWeatherList(hourly, Calendar.HOUR, true);
                                hoursWeatherList.setValue(newHoursWeatherList);
                                buffer.clear();
                            } catch (IOException e) {

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private void updateDaysWeatherList() {
        // Подготавливаем запрос для загрузки прогноза погоды на 10 дней
        Call<ResponseBody> weatherDays = service.getWeatherDaysForecast(
                latitude,
                longitude,
                "weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max,wind_direction_10m_dominant",
                "auto",
                10,
                "ms",
                "flatbuffers"
        );
        // Загрузка прогноза погоды на ближайшие дни
        weatherDays.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try (ResponseBody body = response.body()){
                        if (body != null){
                            try {
                                byte[] responseIn = body.bytes();
                                ByteBuffer buffer = ByteBuffer.wrap(responseIn).order(ByteOrder.LITTLE_ENDIAN);
                                WeatherApiResponse apiResponse = WeatherApiResponse.getRootAsWeatherApiResponse((ByteBuffer) buffer.position(4));
                                VariablesWithTime daily = apiResponse.daily();
                                ArrayList<Weather> newDaysWeatherList = processingWeatherList(daily, Calendar.DAY_OF_YEAR, false);
                                daysWeatherList.setValue(newDaysWeatherList);
                                buffer.clear();
                            } catch (IOException e) {

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
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
}