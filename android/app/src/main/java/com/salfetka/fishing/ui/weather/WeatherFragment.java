package com.salfetka.fishing.ui.weather;

import android.annotation.SuppressLint;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.databinding.FragmentWeatherBinding;
import com.salfetka.fishing.models.weather.UnitMeasure;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        weatherViewModel.getWeather().observe(getViewLifecycleOwner(), weather -> {
            UnitMeasure unitMeasure = weatherViewModel.getUnitMeasure().getValue();
            if (weather != null && unitMeasure != null) {
                binding.lastUpdatedWeather.setText(weather.getWeatherDateTimeFormat());
                binding.currentTemperature.setText(weather.getTemperature()+unitMeasure.getTemperatureUnit());
                binding.currentWeather.setText(weather.getCurrentWeather().getName());
                binding.maxTemperature.setText(weather.getMaxTemperature()+unitMeasure.getTemperatureUnit());
                binding.minTemperature.setText(weather.getMinTemperature()+unitMeasure.getTemperatureUnit());
                binding.chanceOfPrecipitation.setText(weather.getChanceOfPrecipitation()+"%");
                binding.windOrientation.setText(weather.getWindOrientation().getFullName());
                binding.imageCurrentWind.setRotation(weather.getWindOrientation().getAngle());
                binding.windPower.setText(weather.getWindSpeed()+unitMeasure.getSpeedUnit());
                binding.humidity.setText(weather.getHumidity()+"%");
                binding.pressure.setText(weather.getPressure()+unitMeasure.getPressureUnit());
            }
        });
        weatherViewModel.getSunTimes().observe(getViewLifecycleOwner(), sunTimes -> {
            if (sunTimes != null){
                binding.dawn.setText(sunTimes.getDawn());
                binding.sunrise.setText(sunTimes.getSunrise());
                binding.sunset.setText(sunTimes.getSunset());
                binding.twilight.setText(sunTimes.getTwilight());
            }
        });
        final WeatherAdapter hoursAdapter = new WeatherAdapter(getContext(), weatherViewModel.getHoursWeatherList().getValue(), false);
        final WeatherAdapter daysAdapter = new WeatherAdapter(getContext(), weatherViewModel.getDaysWeatherList().getValue(), true);
        binding.hoursWeather.setAdapter(hoursAdapter);
        binding.daysWeather.setAdapter(daysAdapter);
        weatherViewModel.getHoursWeatherList().observe(getViewLifecycleOwner(), hoursWeatherList -> {
            if (hoursWeatherList != null) {
                hoursAdapter.updateData(hoursWeatherList);
            }
        });
        weatherViewModel.getDaysWeatherList().observe(getViewLifecycleOwner(), daysWeatherList -> {
            if (daysWeatherList != null) {
                daysAdapter.updateData(daysWeatherList);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}