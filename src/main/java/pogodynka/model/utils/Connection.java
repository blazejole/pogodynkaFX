package pogodynka.model.utils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    private static Connection ourInstance = new Connection();

    public static Connection getOurInstance(){
        return ourInstance;
    }

    private java.sql.Connection connection;

    private static final String SQL_LINK = "jdbc:mysql://5.135.218.27:3306/BlazejOlesiak?characterEncoding=utf8";
    private static final String SQL_USER = "BlazejOlesiak";
    private static final String SQL_PASS = "????????";
    private static final String SQL_CLASS = "com.mysql.jdbc.Driver";

    private Connection(){
        connect();
        System.out.println("Połączono");
    }

    private void connect() {
        try {
            Class.forName(SQL_CLASS).newInstance();
            connection = DriverManager.getConnection(SQL_LINK,SQL_USER,SQL_PASS);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getNewStatement(){
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PreparedStatement getNewPreparedStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
