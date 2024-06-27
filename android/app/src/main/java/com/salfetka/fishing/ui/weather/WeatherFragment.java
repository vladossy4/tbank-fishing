package com.salfetka.fishing.ui.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.databinding.FragmentWeatherBinding;
import com.salfetka.fishing.models.weather.UnitMeasure;

/** Подложка интерфейса: отображение данных о погоде и обработка событий */
public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        UnitMeasure unitMeasure = weatherViewModel.getUnitMeasure().getValue();
        weatherViewModel.getWeather().observe(getViewLifecycleOwner(), weather -> {
            if (weather != null && unitMeasure != null) {
                binding.lastUpdatedWeather.setText(weather.getWeatherDateTimeFormat());
                binding.currentTemperature.setText(weather.getTemperature()+unitMeasure.getTemperatureUnit());
                binding.currentWeather.setText(weather.getCurrentWeather().getName());
                binding.imageCurrentWeather.setImageResource(weather.getCurrentWeather().getIcon());
                binding.maxTemperature.setText(weather.getMaxTemperature()+unitMeasure.getTemperatureUnit());
                binding.minTemperature.setText(weather.getMinTemperature()+unitMeasure.getTemperatureUnit());
                binding.chanceOfPrecipitation.setText(weather.getChanceOfPrecipitation()+"%");
                binding.windOrientation.setText(weather.getWindOrientation().getFullName());
                binding.imageCurrentWind.setRotation(weather.getWindOrientation().getAngle());
                binding.windPower.setText(weather.getWindSpeed()+unitMeasure.getSpeedUnit());
            }
        });
        weatherViewModel.getOtherWeather().observe(getViewLifecycleOwner(), otherWeather -> {
            if (otherWeather != null && unitMeasure != null){
                binding.windGust.setText(otherWeather.getWindGust()+unitMeasure.getSpeedUnit());
                binding.pressure.setText(otherWeather.getPressure()+unitMeasure.getPressureUnit());
                binding.humidity.setText(otherWeather.getHumidity()+"%");
                binding.dewPoint.setText(otherWeather.getDewPoint()+unitMeasure.getTemperatureUnit());
                binding.sunrise.setText(otherWeather.getSunriseString());
                binding.sunset.setText(otherWeather.getSunsetString());
            }
        });
        final WeatherAdapter hoursAdapter = new WeatherAdapter(getContext(), weatherViewModel.getHoursWeatherList().getValue(), false);
        final WeatherAdapter daysAdapter = new WeatherAdapter(getContext(), weatherViewModel.getDaysWeatherList().getValue(), true);
        binding.hoursWeather.setAdapter(hoursAdapter);
        binding.daysWeather.setAdapter(daysAdapter);
        weatherViewModel.getHoursWeatherList().observe(getViewLifecycleOwner(), hoursAdapter::updateData);
        weatherViewModel.getDaysWeatherList().observe(getViewLifecycleOwner(), daysAdapter::updateData);
        binding.weatherSwipeRefresh.setOnRefreshListener( () -> {
            weatherViewModel.updateWeather();
            binding.weatherSwipeRefresh.setRefreshing(false);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}