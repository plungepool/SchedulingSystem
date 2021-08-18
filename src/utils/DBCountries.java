package utils;

import model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBCountries {

    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries c = new Countries(countryID, countryName);
                countriesList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return countriesList;
    }

    public static void checkDateConversion() {
        System.out.println("Creating date test...");
        String sql = "SELECT Create_Date FROM countries";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
