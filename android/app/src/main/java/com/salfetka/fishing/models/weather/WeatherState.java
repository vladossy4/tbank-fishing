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
}
