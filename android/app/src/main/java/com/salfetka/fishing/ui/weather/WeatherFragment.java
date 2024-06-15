package com.salfetka.fishing.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textDay = binding.textDay;
        final TextView mainTemperature = binding.mainTemperature;
        final TextView mainWeather = binding.mainWeather;
        weatherViewModel.getTextDay().observe(getViewLifecycleOwner(), textDay::setText);
        weatherViewModel.getMainTemperature().observe(getViewLifecycleOwner(), mainTemperature::setText);
        weatherViewModel.getMainWeather().observe(getViewLifecycleOwner(), mainWeather::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}