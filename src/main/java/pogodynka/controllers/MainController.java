package pogodynka.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pogodynka.model.WeatherData;
import pogodynka.model.WeatherStat;
import pogodynka.model.dao.WeatherStatDao;
import pogodynka.model.dao.impl.WeatherStatDaouImpl;
import pogodynka.model.service.IWeatherObserver;
import pogodynka.model.service.WeatherService;
import pogodynka.model.utils.Connection;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements IWeatherObserver, Initializable {
    private Connection connector = Connection.getOurInstance();
    private WeatherStatDao weatherStatDao = new WeatherStatDaouImpl();

    @FXML
    Button searchButton;
    @FXML
    TextField cityTempText;
    @FXML
    TextField whichCityText;
    @FXML
    ProgressIndicator progressLoad;
    @FXML
    Button statsButton;

    private WeatherService weatherService = WeatherService.getService();
    private String lastCityName;

    @Override
    public void onWeatherUpdate(WeatherData data) {
        weatherStatDao.saveStat(new WeatherStat(lastCityName, (float) data.getTemp()));
        cityTempText.setText("temperatura [K] : "+ (double)data.getTemp());
        progressLoad.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            weatherService.registerObserver(this);
        registerShowButton();
        registerEnterListener();
        registerButtonStatsAction();


    }
    private void registerShowButton(){
        searchButton.setOnMouseClicked(e->weatherService.inIt(whichCityText.getText()));
    }
    private void registerEnterListener(){
        whichCityText.setOnKeyPressed(s->{
            if(s.getCode()== KeyCode.ENTER){
                prepareRequestAndClear();
            }
        });
    }

    private void prepareRequestAndClear(){
        lastCityName = whichCityText.getText();
progressLoad.setVisible(true);
    weatherService.inIt(whichCityText.getText());
        whichCityText.clear();
    }
    private void registerButtonStatsAction(){
        statsButton.setOnMouseClicked(e->{
            Stage stage = (Stage)statsButton.getScene().getWindow();
                    try {
                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("stats.fxml"));
                        stage.setScene(new Scene(root,600,400));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
        );
    }
}
