package pogodynka.model;

public class WeatherStat {
    private String cityName;
    private float temp;

    public WeatherStat(String cityName, float temp) {
        this.cityName = cityName;
        this.temp = temp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
