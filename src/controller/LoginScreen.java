package controller;

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
import model.Countries;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            String sql = "SELECT User_ID FROM users WHERE User_Name = \"" + username + "\" AND Password = \"" + password + "\"";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();
            rs.next();

            if (rs.getInt("User_ID") > 0) {
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
    }
}
