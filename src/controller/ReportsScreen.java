package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import utils.DBConnection;

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
import java.util.concurrent.TimeUnit;

public class ReportsScreen implements Initializable {
    public ComboBox<String> totalNumberType;
    public ComboBox<String> totalNumberMonth;
    public Label totalNumberDisplay;
    public ComboBox scheduleContact;
    public TableView scheduleTable;
    public ComboBox<String> totalTimeType;
    public ComboBox<String> totalTimeMonth;
    public Label totalTimeDisplay;

    ObservableList<String> type = FXCollections.observableArrayList();
    ObservableList<String> months = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                type.add(rs.getString("Type"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        totalNumberType.setItems(type);
        totalTimeType.setItems(type);
        months.addAll("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
        totalNumberMonth.setItems(months);
        totalTimeMonth.setItems(months);

    }

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
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
        }

        else {
            LocalDateTime startFilter;
            LocalDateTime endFilter;
            switch(totalNumberMonth.getValue()) {
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
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            totalNumberDisplay.setText(String.valueOf(appointmentCount));
        }
    }

    public void onScheduleButton(ActionEvent actionEvent) {
    }

    public void onTotalTimeButton(ActionEvent actionEvent) {
        if (totalTimeType.getValue() == null || totalTimeMonth.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Please select both type and month.");
            error.setContentText("Press ok to retry.");
            error.showAndWait();
        }

        else {
            LocalDateTime startFilter;
            LocalDateTime endFilter;
            switch(totalTimeMonth.getValue()) {
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
                    System.out.println(appointmentTimeSeconds);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            int hours = 0;
            int minutes = ((int)appointmentTimeSeconds) / 60;
            appointmentTimeSeconds = appointmentTimeSeconds - minutes*60;
            int seconds = ((int)appointmentTimeSeconds);

            if (minutes > 60) {
                hours = minutes / 60;
                minutes = minutes - (hours * 60);
            }

            totalTimeDisplay.setText(hours + "h " + minutes + "m " + seconds + "s");
        }
    }
}
