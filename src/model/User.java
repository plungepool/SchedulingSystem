package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class represents an individual user.*/
public class User {
    private int id;
    private String name;
    private String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** Gets list of all users.
     @return Returns observable list of all user data.*/
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");

                User u = new User(id, name, password);
                usersList.add(u);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    /** Gets ID of user based on name of user.
     @return Returns int user ID.
     @param userName String of username. */
    public static int getUserIdFromUserName(String userName) {
        int userID = 0;
        try {
            String sql = "SELECT User_ID FROM users WHERE User_Name = '" + userName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userID = rs.getInt("User_ID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }

    /** Gets name of user based on user ID.
     @return Returns string name of corresponding user.
     @param userID ID of user. */
    public static String getUserNameFromUserID(int userID) {
        String userName = "";
        try {
            String sql = "SELECT User_Name FROM users WHERE User_ID = " + userID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userName = rs.getString("User_Name");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }
}