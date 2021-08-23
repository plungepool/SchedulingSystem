package utils;

import model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/** Class for database functions related to Contacts.*/
public class DBContacts {

    /** Gets list of all contacts.
     @return Returns observable lists of all available contacts. */
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

    /** Gets ID of contact based on name of contact.
     @return Returns int contact ID.
     @param contactName String of contact name. */
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

    /** Gets name of contact based on contact ID.
     @return Returns string name of corresponding contact.
     @param contactID ID of contact. */
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
