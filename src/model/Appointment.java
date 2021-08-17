package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import utils.DBConnection;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customer_id;
    private int user_id;
    private int contact_id;

    public Appointment(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                appointmentsList.add(a);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    public static ObservableList<Appointment> filterByMonth(String month) {
        LocalDateTime startFilter;
        LocalDateTime endFilter;

        switch(month) {
            case "JAN":
                startFilter = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.JANUARY, 31, 23, 59, 59);
                break;
            case "FEB":
                startFilter = LocalDateTime.of(2021, Month.FEBRUARY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.FEBRUARY, 28, 23, 59, 59);
                break;
            case "MAR":
                startFilter = LocalDateTime.of(2021, Month.MARCH, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.MARCH, 31, 23, 59, 59);
                break;
            case "APR":
                startFilter = LocalDateTime.of(2021, Month.APRIL, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.APRIL, 30, 23, 59, 59);
                break;
            case "MAY":
                startFilter = LocalDateTime.of(2021, Month.MAY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.MAY, 31, 23, 59, 59);
                break;
            case "JUN":
                startFilter = LocalDateTime.of(2021, Month.JUNE, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.JUNE, 30, 23, 59, 59);
                break;
            case "JUL":
                startFilter = LocalDateTime.of(2021, Month.JULY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.JULY, 31, 23, 59, 59);
                break;
            case "AUG":
                startFilter = LocalDateTime.of(2021, Month.AUGUST, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.AUGUST, 31, 23, 59, 59);
                break;
            case "SEP":
                startFilter = LocalDateTime.of(2021, Month.SEPTEMBER, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.SEPTEMBER, 30, 23, 59, 59);
                break;
            case "OCT":
                startFilter = LocalDateTime.of(2021, Month.OCTOBER, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.OCTOBER, 31, 23, 59, 59);
                break;
            case "NOV":
                startFilter = LocalDateTime.of(2021, Month.NOVEMBER, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.NOVEMBER, 30, 23, 59, 59);
                break;
            case "DEC":
                startFilter = LocalDateTime.of(2021, Month.DECEMBER, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.DECEMBER, 31, 23, 59, 59);
                break;
            default:
                startFilter = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.DECEMBER, 31, 23, 59, 59);
        }

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Start BETWEEN '" + Timestamp.valueOf(startFilter) + "' AND '" + Timestamp.valueOf(endFilter) + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                appointmentsList.add(a);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    public static ObservableList<Appointment> filterByWeekh(String week) {
        Locale locale = Locale.getDefault();
        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        LocalDateTime startFilter = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime endFilter = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

        switch(week) {
            case "THIS WEEK":
                startFilter = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.JANUARY, 31, 23, 59, 59);
                break;
            case "NEXT WEEK":
                startFilter = LocalDateTime.of(2021, Month.FEBRUARY, 1, 0, 0, 0);
                endFilter = LocalDateTime.of(2021, Month.FEBRUARY, 28, 23, 23, 59);
                break;
        }

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Start BETWEEN '" + Timestamp.valueOf(startFilter) + "' AND '" + Timestamp.valueOf(endFilter) + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                appointmentsList.add(a);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

//    public static void addNewCustomer(Customer newCustomer){
//        try {
//            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
//                    "VALUES (" + newCustomer.getId() + ", '" + newCustomer.getName() + "', '" + newCustomer.getAddress() + "', '" +
//                    newCustomer.getPostalcode() + "', '" + newCustomer.getPhone() + "', " + newCustomer.getDivision_id() + ")";
//            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
//            ps.executeUpdate();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void updateCustomer(Customer itemToUpdate) {
//
//    }
//
//    public static void deleteCustomer(Customer itemToDelete) {
//        try {
//            int customerIdToDelete = itemToDelete.getId();
//            String sql = "DELETE FROM customers WHERE Customer_ID = " + customerIdToDelete;
//            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
//            ps.executeUpdate();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
