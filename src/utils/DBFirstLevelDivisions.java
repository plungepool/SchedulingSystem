package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.*;

public class DBFirstLevelDivisions {

    public static ObservableList<FirstLevelDivisions> getAllDivisionsFromCountryID(int selectedCountryId) {
        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + selectedCountryId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                FirstLevelDivisions f = new FirstLevelDivisions(divisionID, divisionName, countryID);
                divisionsList.add(f);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionsList;
    }

    public static String getCountryNameFromDivisionID(int divisionID) {
        String countryName = "";

        try {
            String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = " + divisionID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();
            int countryId = 0;
            while (rs.next()) {
                countryId = rs.getInt("Country_ID");
            }

            String sql2 = "SELECT Country FROM countries WHERE Country_ID = " + countryId;
            PreparedStatement ps2 = DBConnection.getConnection().prepareStatement((sql2));
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                countryName = rs2.getString("Country");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return countryName;
    }

    public static int getCountryIdFromCountryName(String countryName) {
        int countryID = 0;
        try {
            String sql = "SELECT Country_ID FROM countries WHERE Country = '" + countryName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                countryID = rs.getInt("Country_ID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return countryID;
    }

    public static int getDivisionIdFromDivisionName(String divisionName) {
        int divisionID = 0;
        try {
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionID;
    }

    public static String getDivisionNameFromDivisionID(int divisionID) {
        String divisionName = "";
        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = " + divisionID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                divisionName = rs.getString("Division");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionName;
    }
}
