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
            if (weather != null) {
                binding.lastUpdatedWeather.setText(weather.getLastUpdateFormat());
                binding.currentTemperature.setText(weather.getTemperature()+weather.getTemperatureUnit());
                binding.currentWeather.setText(weather.getCurrentWeather());
                binding.maxTemperature.setText(weather.getMaxTemperature()+weather.getTemperatureUnit());
                binding.minTemperature.setText(weather.getMinTemperature()+weather.getTemperatureUnit());
                binding.chanceOfPrecipitation.setText(weather.getChanceOfPrecipitation()+"%");
                binding.windOrientation.setText(weather.getWindOrientation().getFullName());
                binding.imageCurrentWind.setRotation(weather.getWindOrientation().ordinal()*45);
                binding.windPower.setText(weather.getWindSpeed()+weather.getSpeedUnit());
                binding.humidity.setText(weather.getHumidity()+"%");
                binding.pressure.setText(weather.getPressure()+weather.getPressureUnit());
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}