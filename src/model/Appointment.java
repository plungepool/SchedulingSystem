package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

/** This class represents an appointment scheduled with a customer.*/
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

    /** Gets list of all appointments.
     @return Returns observable list of all appointment data.*/
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

    /** Gets list of all appointments by customer.
     @return Returns observable list of all appointment data by customer.
     @param customerID ID of customer to return appointments from. */
    public static ObservableList<Appointment> getAllAppointmentsByCustomer(int customerID) {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;
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

    /** Filters appointments by scheduled month.
     @return Returns observable list of appointment data by month.
     @param month String of 3-letter capitalized month abbreviation to filter by. */
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

    /** Filters appointments by scheduled week.
     @return Returns observable list of appointment data by week.
     @param week String of capitalized week name to filter by. */
    public static ObservableList<Appointment> filterByWeek(String week) {
        Locale locale = Locale.getDefault();
        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        LocalDateTime startFilter = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime endFilter = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

        switch(week) {
            case "THIS WEEK":
                break;
            case "NEXT WEEK":
                startFilter = startFilter.plusDays(7);
                endFilter = endFilter.plusDays(7);
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

    /** Adds new appointment to calendar.
     @param newAppointment New appointment to add to calendar. */
    public static void addNewAppointment(Appointment newAppointment){
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES ('" + newAppointment.getTitle() + "', '" + newAppointment.getDescription() + "', '" +
                    newAppointment.getLocation() + "', '" + newAppointment.getType() + "', '" + newAppointment.getStart() + "', '" +
                    newAppointment.getEnd() + "', " + newAppointment.getCustomer_id() + ", " + newAppointment.getUser_id() + ", " +
                    newAppointment.getContact_id() + ")";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Updates new appointment to calendar.
     @param newAppointment New appointment information to replace in appointment being modified.
     @param itemToModify Appointment to update in calendar. */
    public static void updateAppointment(Appointment newAppointment, Appointment itemToModify){
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (" + itemToModify.getId() + ", '" + newAppointment.getTitle() + "', '" + newAppointment.getDescription() + "', '" +
                    newAppointment.getLocation() + "', '" + newAppointment.getType() + "', '" + newAppointment.getStart() + "', '" +
                    newAppointment.getEnd() + "', " + newAppointment.getCustomer_id() + ", " + newAppointment.getUser_id() + ", " +
                    newAppointment.getContact_id() + ")";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            deleteAppointment(itemToModify);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Deletes appointment from calendar.
     @param itemToDelete Appointment to delete from calendar. */
    public static void deleteAppointment(Appointment itemToDelete) {
        try {
            int appointmentIdToDelete = itemToDelete.getId();
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentIdToDelete;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
