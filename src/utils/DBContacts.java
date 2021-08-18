package utils;

import model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBContacts {
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts c = new Contacts(contactID, contactName, contactEmail);
                contactsList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }

    public static int getContactIdFromContactName(String contactName) {
        int contactID = 0;
        try {
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contactName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contactID = rs.getInt("Contact_ID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contactID;
    }

    public static String getContactNameFromContactID(int contactID) {
        String contactName = "";
        try {
            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contactID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contactName = rs.getString("Contact_Name");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contactName;
    }
}
