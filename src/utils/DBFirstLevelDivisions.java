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
}
