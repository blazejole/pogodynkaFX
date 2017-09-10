package pogodynka.model.service;

import pogodynka.model.WeatherData;

public interface IWeatherObserver {
    void onWeatherUpdate(WeatherData data);
}
