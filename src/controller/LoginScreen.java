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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.io.*;

/** Controller for login screen. */
public class LoginScreen implements Initializable {
    public TextField usernameField;
    public TextField passwordField;
    public Label locationLabel;
    public Button loginButton;
    public Label promptLabel;
    public Label timezoneLabel;
    public Label headerLabel;

    public static ZoneId userZone = ZoneId.systemDefault();
    public static Locale defaultLocale = Locale.getDefault(); //Locale.FRANCE;
    public static String userZoneDisplayName = userZone.getDisplayName(TextStyle.FULL, defaultLocale);
    public static ResourceBundle msg = ResourceBundle.getBundle("resources/login", defaultLocale);;

    /** Sets login language text and timezone information.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationLabel.setText(userZoneDisplayName);
        headerLabel.setText(msg.getString("header"));
        promptLabel.setText(msg.getString("prompt"));
        timezoneLabel.setText(msg.getString("zonedetected"));
        usernameField.setPromptText(msg.getString("username"));
        passwordField.setPromptText(msg.getString("password"));
        loginButton.setText(msg.getString("loginbutton"));
    }

    /** Transitions to main screen.*/
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

    /** Action on login attempt.
     Validates user login and logs all login attempts to text file.*/
    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String loginAttempt = "FAILED";

        try {
            String sql = "SELECT User_ID FROM users WHERE User_Name = \"" + username + "\" AND Password = \"" + password + "\"";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();
            rs.next();

            if (rs.getInt("User_ID") > 0) {
                loginAttempt = "SUCCESS";
                ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
                customerAppointments.addAll(Appointment.getAllAppointments());
                LocalDateTime currentTime = LocalDateTime.now();
                boolean appointmentSoonFlag = false;
                for (Appointment a : customerAppointments) {
                    LocalDateTime thisAppointmentStart = a.getStart().toLocalDateTime();
                    long timeDifference = ChronoUnit.MINUTES.between(currentTime, thisAppointmentStart);
                    if (timeDifference > 0 && timeDifference <= 15) {
                        appointmentSoonFlag = true;
                        Alert appointmentSoon = new Alert(Alert.AlertType.INFORMATION);
                        appointmentSoon.setTitle("Alert");
                        appointmentSoon.setHeaderText("Appointment soon: ID #" + a.getId() + ", on " + thisAppointmentStart.toString().substring(0, 10) + " at " + thisAppointmentStart.toString().substring(11));
                        appointmentSoon.setContentText("Press ok to continue.");
                        appointmentSoon.showAndWait();
                        break;
                    }
                }
                if (!appointmentSoonFlag) {
                    Alert noAppointment = new Alert(Alert.AlertType.INFORMATION);
                    noAppointment.setTitle("Alert");
                    noAppointment.setHeaderText("No appointments starting soon.");
                    noAppointment.setContentText("Press ok to continue.");
                    noAppointment.showAndWait();
                }
                toMainScreen(actionEvent);
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(msg.getString("errorTitle"));
                error.setHeaderText(msg.getString("errorHeader"));
                error.setContentText(msg.getString("errorContent"));
                error.showAndWait();
            }
        }
        catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle(msg.getString("errorTitle"));
            error.setHeaderText(msg.getString("errorHeader"));
            error.setContentText(msg.getString("errorContent"));
            error.showAndWait();
        }
        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter log = new PrintWriter(bw);
            log.println("[" + LocalDateTime.now() + "] - User: " + username + " - " + loginAttempt);
            log.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
