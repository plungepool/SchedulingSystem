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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;
import model.Customer;
import utils.DBContacts;
import utils.DBCustomer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarAddScreen implements Initializable {
    public TextField idField;
    public TextField titleField;
    public TextField descriptionField;
    public ComboBox<String> contactCombo;
    public ComboBox<String> customerCombo;
    public TextField locationField;
    public TextField typeField;
    public DatePicker startField;
    public DatePicker endField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> contactsNames = FXCollections.observableArrayList();
        for (Contacts con : DBContacts.getAllContacts()) {
            contactsNames.add(con.getName());
        }
        contactCombo.setItems(contactsNames);

        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer cus : Customer.getAllCustomers()) {
            customerNames.add(cus.getName());
        }
        customerCombo.setItems(customerNames);
    }

    private int findNewAppointmentID(int newID) {
        ObservableList<Appointment> allAppointments = Appointment.getAllAppointments();
        for (Appointment a : allAppointments) {
            if (a.getId() == newID) {
                ++newID;
                findNewAppointmentID(newID);
            }
        }
        return newID;
    }

    public void onSaveButton(ActionEvent actionEvent) {
        int newID = 1;
        newID = findNewAppointmentID(newID);

        if (idField.getText() == null || titleField.getText() == null || descriptionField.getText() == null || contactCombo.getValue() == null || locationField.getText() == null || typeField.getText() == null || startField.getChronology() == null || endField.getChronology() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Invalid input.");
            error.setContentText("Check values and try again.");
            error.showAndWait();
        }
        else {
            try {
                int contactID = DBContacts.getContactIdFromContactName(contactCombo.getValue().toString());
                int customerID = DBCustomer.getCustomerIdFromCustomerName(customerCombo.getValue().toString());
                int userID = 1;
//                Appointment newAppointment = new Appointment(
//                        newID,
//                        titleField.getText(),
//                        descriptionField.getText(),
//                        locationField.getText(),
//                        typeField.getText(),
//                        startField.getText(),
//                        endField.getText(),
//                        customerID,
//                        userID,
//                        contactID);
//                Appointment.addNewAppointment(newAppointment);
                onBackButton(actionEvent);
            } catch (RuntimeException | IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Server issue.");
                error.setContentText("Unable to complete this action, please try again.");
                error.showAndWait();
            }
        }
    }

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CalendarScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }
}
