package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;
import utils.DBConnection;
import utils.DBContacts;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsScreen implements Initializable {
    public ComboBox<String> totalNumberType;
    public ComboBox<String> totalNumberMonth;
    public Label totalNumberDisplay;
    public ComboBox<String> scheduleContact;
    public TableView<Appointment> scheduleTable;
    public ComboBox<String> totalTimeType;
    public ComboBox<String> totalTimeMonth;
    public Label totalTimeDisplay;
    public TableColumn<Object, Object> scheduleId;
    public TableColumn<Object, Object> scheduleTitle;
    public TableColumn<Object, Object> scheduleDescription;
    public TableColumn<Object, Object> scheduleLocation;
    public TableColumn<Object, Object> scheduleContactId;
    public TableColumn<Object, Object> scheduleType;
    public TableColumn scheduleStart;
    public TableColumn<Object, Object> scheduleEnd;
    public TableColumn<Object, Object> scheduleCustomerId;

    ObservableSet<String> type = FXCollections.observableSet();
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<String> contacts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String sqlA = "SELECT * FROM appointments";
            PreparedStatement psA = DBConnection.getConnection().prepareStatement((sqlA));
            ResultSet rsA = psA.executeQuery();

            while (rsA.next()) {
                type.add(rsA.getString("Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        totalNumberType.setItems(FXCollections.observableArrayList(type));
        totalTimeType.setItems(FXCollections.observableArrayList(type));
        months.addAll("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
        totalNumberMonth.setItems(months);
        totalTimeMonth.setItems(months);

        try {
            String sqlC = "SELECT * FROM contacts";
            PreparedStatement psC = DBConnection.getConnection().prepareStatement((sqlC));
            ResultSet rsC = psC.executeQuery();

            while (rsC.next()) {
                contacts.add(rsC.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scheduleContact.setItems(contacts);
    }

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

    public void onTotalNumberButton(ActionEvent actionEvent) {
        if (totalNumberType.getValue() == null || totalNumberMonth.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Please select both type and month.");
            error.setContentText("Press ok to retry.");
            error.showAndWait();
        } else {
            LocalDateTime startFilter;
            LocalDateTime endFilter;
            switch (totalNumberMonth.getValue()) {
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

            int appointmentCount = 0;
            try {
                String sql = "SELECT COUNT(*) FROM appointments WHERE Type = '" + totalNumberType.getValue() + "' AND Start BETWEEN '" + startFilter + "' AND '" + endFilter + "'";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    appointmentCount = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            totalNumberDisplay.setText(String.valueOf(appointmentCount));
        }
    }

    public void onTotalTimeButton(ActionEvent actionEvent) {
        if (totalTimeType.getValue() == null || totalTimeMonth.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Please select both type and month.");
            error.setContentText("Press ok to retry.");
            error.showAndWait();
        } else {
            LocalDateTime startFilter;
            LocalDateTime endFilter;
            switch (totalTimeMonth.getValue()) {
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

            long appointmentTimeSeconds = 0;
            try {
                String sql = "SELECT * FROM appointments WHERE Type = '" + totalTimeType.getValue() + "' AND Start BETWEEN '" + startFilter + "' AND '" + endFilter + "'";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    LocalDateTime thisAppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime thisAppointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                    long timeDifference = ChronoUnit.SECONDS.between(thisAppointmentStart, thisAppointmentEnd);
                    appointmentTimeSeconds = appointmentTimeSeconds + timeDifference;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int hours = 0;
            int minutes = ((int) appointmentTimeSeconds) / 60;
            appointmentTimeSeconds = appointmentTimeSeconds - minutes * 60;
            int seconds = ((int) appointmentTimeSeconds);

            if (minutes > 60) {
                hours = minutes / 60;
                minutes = minutes - (hours * 60);
            }

            totalTimeDisplay.setText(hours + "h " + minutes + "m " + seconds + "s");
        }
    }

    public void onScheduleButton(ActionEvent actionEvent) {
        if (scheduleContact.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Please select a contact.");
            error.setContentText("Press ok to retry.");
            error.showAndWait();
        } else {
            String contactName = scheduleContact.getValue();
            int contactId = 0;

            try {
                String sqlC = "SELECT * FROM contacts WHERE Contact_Name = '" + scheduleContact.getValue() + "'";
                PreparedStatement psC = DBConnection.getConnection().prepareStatement((sqlC));
                ResultSet rsC = psC.executeQuery();
                while (rsC.next()) {
                    contactId = rsC.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
            try {
                String sqlA = "SELECT * FROM appointments WHERE Contact_ID = " + contactId;
                PreparedStatement psA = DBConnection.getConnection().prepareStatement((sqlA));
                ResultSet rsA = psA.executeQuery();
                while (rsA.next()) {
                    int id = rsA.getInt("Appointment_ID");
                    String title = rsA.getString("Title");
                    String description = rsA.getString("Description");
                    String location = rsA.getString("Location");
                    String type = rsA.getString("Type");
                    Timestamp start = rsA.getTimestamp("Start");
                    Timestamp end = rsA.getTimestamp("End");
                    int customer_id = rsA.getInt("Customer_ID");
                    int user_id = rsA.getInt("User_ID");
                    int contact_id = rsA.getInt("Contact_ID");

                    Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                    contactAppointments.add(a);
                }
                scheduleTable.setItems(contactAppointments);
                scheduleId.setCellValueFactory(new PropertyValueFactory<>("id"));
                scheduleTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                scheduleDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                scheduleLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
                scheduleContactId.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
                scheduleType.setCellValueFactory(new PropertyValueFactory<>("type"));
                scheduleStart.setCellValueFactory(new PropertyValueFactory<>("start"));
                scheduleEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
                scheduleCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

                scheduleTable.getSortOrder().add(scheduleStart);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}