package com.salfetka.fishing.models.weather;

import com.salfetka.fishing.R;

/** Связывает название погодных условий с отображаемыми значками */
public enum WeatherState {
    Sunny("Ясно", R.drawable.ic_weather_sunny_48dp),
    ClearNight("Ясно", R.drawable.ic_weather_clear_night_48dp),
    PartlyCloudy("Переменная\nоблачность", R.drawable.ic_weather_partly_cloudy_48dp),
    PartlyCloudyNight("Переменная\nоблачность", R.drawable.ic_weather_nights_cloudy_48dp),
    Cloudy("Облачно", R.drawable.ic_weather_cloudy_48dp),
    Rainy("Дождь", R.drawable.ic_weather_rainy_48dp),
    Snowy("Снег", R.drawable.ic_weather_snowy_48dp),
    Thunderstorm("Гроза", R.drawable.ic_weather_thunderstorm_48dp);

    private final String name;
    private final int icon;

    WeatherState(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public static WeatherState getWeatherState(int code, boolean isDay) {
        if (code == 0 || code == 1) {
            if (isDay) return WeatherState.Sunny;
            else return WeatherState.ClearNight;
        } else if (code == 2) {
            if (isDay) return WeatherState.PartlyCloudy;
            else return WeatherState.PartlyCloudyNight;
        } else if (code == 3) return WeatherState.Cloudy;
        else if ((code>=51 && code<=67) || (code>=80 && code<=82)) return WeatherState.Rainy;
        else if ((code>=71 && code<=77) || (code>=85 && code<=86)) return WeatherState.Snowy;
        else if (code>=95 && code<=99) return WeatherState.Thunderstorm;
        else return WeatherState.Cloudy;
    }
}
