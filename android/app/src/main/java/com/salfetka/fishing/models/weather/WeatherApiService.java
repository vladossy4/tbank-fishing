package com.salfetka.fishing.models.weather;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("/v1/forecast")
    Call<ResponseBody> getWeatherForecast(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("daily") String daily,
            @Query("timezone") String timezone,
            @Query("forecast_days") int numOfDays,
            @Query("wind_speed_unit") String windUnit,
            @Query("format") String format
    );
}
