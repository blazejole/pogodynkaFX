package pogodynka.model.service;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.json.JSONObject;
import pogodynka.Config;
import pogodynka.Utils;
import pogodynka.model.WeatherData;

import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class WeatherService {// wzorzez Singleton
    private static WeatherService INSTANCE = new WeatherService();
    public static WeatherService getService(){
        return INSTANCE;
    }


    private List<IWeatherObserver> observerList;
private ExecutorService executorService;
    private WeatherService(){
        executorService = Executors.newFixedThreadPool(2);
        observerList = new ArrayList<>();


    }

    public void registerObserver(IWeatherObserver observer){
        observerList.add(observer);
    }

    private void notifyObservers(WeatherData data){
        for(IWeatherObserver iWeatherObserver : observerList){
            iWeatherObserver.onWeatherUpdate(data);
        }
    }


    public void inIt(final String city){
        Runnable taskInit = () -> {

                String text = Utils.readWebsideContex(Config.APP_URL + city + "&appid=" + Config.APP_KEY);//
                parseJsonFromString(text);
        };
        executorService.execute(taskInit);
    }

    private void parseJsonFromString(String text) {

        WeatherData data = new WeatherData();
        JSONObject root = new JSONObject(text);
        JSONObject main = root.getJSONObject("main");
        data.setTemp(main.getDouble("temp"));
        data.setHumidity(main.getInt("humidity"));
        data.setPressure(main.getInt("pressure"));


        JSONObject cloudsObj = root.getJSONObject("clouds");
        data.setClouds(cloudsObj.getInt("all"));
        notifyObservers(data);
    }
}
