package com.salfetka.fishing.ui.weather;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.databinding.FragmentWeatherBinding;
import com.salfetka.fishing.models.SharedPreferencesHelper;
import com.salfetka.fishing.models.weather.Address;
import com.salfetka.fishing.models.weather.NominatimApi;
import com.salfetka.fishing.models.weather.UnitMeasure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Подложка интерфейса: отображение данных о погоде и обработка событий */
public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    private static final String BASE_URL = "https://nominatim.openstreetmap.org/";

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.setContext(requireContext());
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NominatimApi nominatimApi = retrofit.create(NominatimApi.class);

        List<String> cities = readFileFromAssets(requireContext(), "city.txt");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, cities);
        binding.locationPicker.setAdapter(arrayAdapter);
        binding.locationPicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String city = s.toString();
                if (cities.contains(city)) {
                    Call<List<Address>> call = nominatimApi.search(city, "Russia", "json", 1, 1, "ru");
                    call.enqueue(new Callback<List<Address>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                            if (response.isSuccessful()) {
                                List<Address> addressList = response.body();
                                if (addressList != null) {
                                    weatherViewModel.setLocation(addressList.get(0).getLat(), addressList.get(0).getLon());
                                    SharedPreferencesHelper.saveObject(requireContext(), "city", city);
                                }
                            } else {
                                Log.i("Location", "Request failed with code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Address>> call, Throwable throwable) {
                            Log.i("FAIL", "onFailure: " + throwable.getMessage());
                        }
                    });
                }
            }
        });
        String city = SharedPreferencesHelper.getObject(requireContext(), "city", String.class);
        if (city != null) binding.locationPicker.setText(city);
        else binding.locationPicker.setText("Москва");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<String> readFileFromAssets(Context context, String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try (InputStream inputStream = assetManager.open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Log.e("READ FILE", "readFileFromAssets: " + e.getMessage());
        }

        return lines;
    }
}