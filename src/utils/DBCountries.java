package utils;

import model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/** Class for database functions related to Countries.*/
public class DBCountries {

    /** Gets list of all countries.
     @return Returns observable lists of all available countries. */
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

}
