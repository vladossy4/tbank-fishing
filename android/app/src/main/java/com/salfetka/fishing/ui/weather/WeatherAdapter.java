package com.salfetka.fishing.ui.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salfetka.fishing.R;
import com.salfetka.fishing.models.Weather;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Weather> weatherList;
    private boolean forDay;

    WeatherAdapter(Context context, List<Weather> weatherList, boolean forDay) {
        this.weatherList = weatherList;
        this.inflater = LayoutInflater.from(context);
        this.forDay = forDay;
    }
    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        if (forDay){
            holder.title.setText(weather.getWeatherDayFormat());
            holder.temperature.setText(weather.getMaxTemperature()+"/"+weather.getMinTemperature());
        }
        else {
            holder.title.setText(weather.getWeatherHourFormat());
            holder.temperature.setText(String.valueOf(weather.getTemperature()));
        }
        holder.weatherIcon.setImageResource(R.drawable.ic_weather_sunny_48dp);
        holder.windIcon.setRotation(weather.getWindOrientation().getAngle());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView weatherIcon, windIcon;
        final TextView title, temperature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.weather_item_title);
            temperature = itemView.findViewById(R.id.weather_item_temperature);
            weatherIcon = itemView.findViewById(R.id.item_current_weather);
            windIcon = itemView.findViewById(R.id.item_current_wind);
        }
    }
}
