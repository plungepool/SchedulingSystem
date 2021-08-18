package utils;

import model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBCustomer {
    public static int getCustomerIdFromCustomerName(String customerName) {
        int customerID = 0;
        try {
            String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = '" + customerName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerID = rs.getInt("Customer_ID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customerID;
    }
}
