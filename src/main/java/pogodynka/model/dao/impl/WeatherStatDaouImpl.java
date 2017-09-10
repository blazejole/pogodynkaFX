package pogodynka.model.dao.impl;

import pogodynka.model.WeatherStat;
import pogodynka.model.dao.WeatherStatDao;
import pogodynka.model.utils.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherStatDaouImpl implements WeatherStatDao {
    private Connection connection = Connection.getOurInstance();
    @Override
    public void saveStat(WeatherStat weatherStat) {
        PreparedStatement preparedStatement = connection.getNewPreparedStatement(" INSERT INTO weather VALUES(?,?,?) ");
        try {
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2,weatherStat.getCityName());
            preparedStatement.setFloat(3,weatherStat.getTemp());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<WeatherStat> getLastSixStats(String cityname) {
        List<WeatherStat> weatherStatList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.getNewPreparedStatement("SELECT * FROM weather WHERE city = ? ORDER BY id DESC LIMIT 6");
        WeatherStat weatherStat;
        try {
            preparedStatement.setString(1,cityname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                weatherStat = new WeatherStat(resultSet.getString("city"), resultSet.getFloat("temp"));
                weatherStatList.add(weatherStat);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weatherStatList;
    }

    @Override
    public List<String> getAllCities() {
        List<String> allCities = new ArrayList<>();
        PreparedStatement preparedStatement = connection.getNewPreparedStatement("SELECT DISTINCT city FROM weather");
        try {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                allCities.add(resultSet.getString("city"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCities;
    }

    @Override
    public double countAvgTempCity(String city) {
        List<Double> allTemp = new ArrayList<>();
        double average = 0;
        PreparedStatement preparedStatement = connection.getNewPreparedStatement("SELECT temp FROM weather WHERE city=?");
        try {
            preparedStatement.setString(1,city);
            //preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allTemp.add(resultSet.getDouble("temp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double sum = 0;
        for (Double temp : allTemp) {
          sum+=temp;
        }
        average = sum/allTemp.size();
        return average;
    }


}
