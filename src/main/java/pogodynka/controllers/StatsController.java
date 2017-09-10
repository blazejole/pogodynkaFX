package pogodynka.controllers;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pogodynka.model.WeatherStat;
import pogodynka.model.dao.WeatherStatDao;
import pogodynka.model.dao.impl.WeatherStatDaouImpl;
import pogodynka.model.utils.Connection;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable{

    @FXML
    BarChart<String, Number> charWeather;
    @FXML
    ListView<String> listOfCity;
    @FXML
    Button backMenuButton;
    @FXML
    TextField avgTempText;

    private Connection connection = Connection.getOurInstance();
    private WeatherStatDao weatherStatDao = new WeatherStatDaouImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadCitiesToList();
        registerClickItemOnList();
        goBackToMenu();
        showAvarageTemperature();
    }

    private void goBackToMenu(){
        backMenuButton.setOnMouseClicked(s->{
            Stage stage = (Stage)backMenuButton.getScene().getWindow();
                    try {
                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pogodynka.fxml"));
                        stage.setScene(new Scene(root, 600,400));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void loadCitiesToList(){
        listOfCity.setItems(FXCollections.observableList(weatherStatDao.getAllCities()));
    }
    private void registerClickItemOnList() {
        listOfCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadChar(newValue);
        });
    }

    private void loadChar(String city){
XYChart.Series series1 = new XYChart.Series();
series1.setName(city);
int counter = 1;
for(WeatherStat weatherStat: weatherStatDao.getLastSixStats(city)){
    series1.getData().add(new XYChart.Data(""+counter, weatherStat.getTemp()));
    counter++;
        }
        charWeather.getData().clear();
        charWeather.getData().add(series1);
    }

   private void showAvarageTemperature(){

       listOfCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            whichCityAvgTemp(newValue);
        });
   }

    private void whichCityAvgTemp(String newValue) {
        avgTempText.setText(String.valueOf(weatherStatDao.countAvgTempCity(newValue)));
    }
}

