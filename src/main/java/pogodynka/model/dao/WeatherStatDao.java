package pogodynka.model.dao;

import pogodynka.model.WeatherStat;

import java.util.List;

public interface WeatherStatDao {
    void saveStat (WeatherStat weatherStat);
    List<WeatherStat> getLastSixStats(String cityname);
    List<String> getAllCities();
    double countAvgTempCity(String city);


}
