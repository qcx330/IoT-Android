package com.example.test;

import com.example.test.model.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnection {
    private static final String DB_URL = "jdbc:mysql://your_mysql_server:3306/agriculture";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Admin@123";
    public List<Data> getDataFromMySQL() {
        List<Data> dataPoints = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // Execute your SQL query to fetch data
            String query = "SELECT station_name FROM weather_data";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String date = resultSet.getString("timestamp");
                Data dt = new Data();
                dt.setTime(date);
                dataPoints.add(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dataPoints;
    }
}
