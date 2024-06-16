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
                binding.currentTemperature.setText(weather.getCurrentTemperature()+weather.getUnitMeasure());
                binding.currentWeather.setText(weather.getCurrentWeather());
                binding.maxTemperature.setText(weather.getMaxTemperature()+weather.getUnitMeasure());
                binding.minTemperature.setText(weather.getMinTemperature()+weather.getUnitMeasure());
                binding.chanceOfPrecipitation.setText(weather.getChanceOfPrecipitation()+"%");
                binding.windOrientation.setText(weather.getWindOrientation());
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