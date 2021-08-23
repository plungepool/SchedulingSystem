package utils;

import java.sql.*;

/** Class for database functions related to Customers.*/
public class DBCustomer {

    /** Gets ID of customer based on name of customer.
     @return Returns int customer ID.
     @param customerName String of customer name. */
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

    /** Gets name of customer based on customer ID.
     @return Returns string name of corresponding customer.
     @param customerID ID of customer. */
    public static String getCustomerNameFromCustomerID(int customerID) {
        String customerName = "";
        try {
            String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerName = rs.getString("Customer_Name");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customerName;
    }
}
