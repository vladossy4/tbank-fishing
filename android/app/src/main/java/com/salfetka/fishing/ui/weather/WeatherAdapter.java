package com.salfetka.fishing.ui.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salfetka.fishing.R;
import com.salfetka.fishing.models.weather.Weather;

import java.util.ArrayList;
import java.util.List;

/** Связывает состояния погоды в списке с их представлением */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Weather> weatherList;
    private final boolean forDay;

    WeatherAdapter(Context context, List<Weather> weatherList, boolean forDay) {
        if (weatherList != null) {
            this.weatherList = weatherList;
        }
        else this.weatherList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.forDay = forDay;
    }

    public void updateData(List<Weather> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
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
        holder.weatherIcon.setImageResource(weather.getCurrentWeather().getIcon());
        holder.windIcon.setRotation(weather.getWindOrientation().getAngle());
        holder.windOrientation.setText(weather.getWindOrientation().getShortName());
        holder.amountOfPrecipitation.setText(String.format("%.2f", weather.getAmountOfPrecipitation()));
        holder.windSpeed.setText(String.valueOf(weather.getWindSpeed()));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView weatherIcon, windIcon;
        final TextView title, temperature, windOrientation, amountOfPrecipitation, windSpeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.weather_item_title);
            temperature = itemView.findViewById(R.id.weather_item_temperature);
            weatherIcon = itemView.findViewById(R.id.item_current_weather);
            windIcon = itemView.findViewById(R.id.item_current_wind);
            windOrientation = itemView.findViewById(R.id.weather_item_wind_orientation);
            amountOfPrecipitation = itemView.findViewById(R.id.weather_item_amount_of_precipitation);
            windSpeed = itemView.findViewById(R.id.weather_item_wind_speed);
        }
    }
}
