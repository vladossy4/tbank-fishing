package com.salfetka.fishing.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.databinding.FragmentWeatherBinding;
import com.salfetka.fishing.models.Weather;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView lastUpdatedWeather = binding.lastUpdatedWeather;
        final TextView currentTemperature = binding.currentTemperature;
        final TextView currentWeather = binding.currentWeather;
        final TextView maxTemperature = binding.maxTemperature;
        final TextView minTemperature = binding.minTemperature;
        weatherViewModel.getWeather().observe(getViewLifecycleOwner(), weather -> {
            if (weather != null) {
                lastUpdatedWeather.setText(weather.getLastUpdate());
                currentTemperature.setText(weather.addUnitMeasure(weather.getCurrentTemperature()));
                currentWeather.setText(weather.getCurrentWeather());
                maxTemperature.setText(weather.addUnitMeasure(weather.getMaxTemperature()));
                minTemperature.setText(weather.addUnitMeasure(weather.getMinTemperature()));
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