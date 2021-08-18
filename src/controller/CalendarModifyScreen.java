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
import model.*;
import utils.DBContacts;
import utils.DBCustomer;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarModifyScreen implements Initializable {
    public TextField idField;
    public TextField titleField;
    public TextField descriptionField;
    public ComboBox<String> contactCombo;
    public ComboBox<String> customerCombo;
    public TextField locationField;
    public TextField typeField;
    public DatePicker datePicker;
    public TextField startField;
    public TextField endField;

    public static Appointment itemToModify;

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

        idField.setText(String.valueOf(itemToModify.getId()));
        titleField.setText(String.valueOf(itemToModify.getTitle()));
        descriptionField.setText(String.valueOf(itemToModify.getDescription()));
        locationField.setText(String.valueOf(itemToModify.getLocation()));
        typeField.setText(String.valueOf(itemToModify.getType()));

        String contactName = DBContacts.getContactNameFromContactID(itemToModify.getContact_id());
        contactCombo.setValue(contactName);

        String customerName = DBCustomer.getCustomerNameFromCustomerID(itemToModify.getCustomer_id());
        customerCombo.setValue(customerName);

        datePicker.setValue(itemToModify.getStart().toLocalDateTime().toLocalDate());

        startField.setText(itemToModify.getStart().toString().substring(11, 19));
        endField.setText(itemToModify.getEnd().toString().substring(11, 19));
    }

    public void onSaveButton(ActionEvent actionEvent) {
        if (idField.getText() == null || titleField.getText() == null || descriptionField.getText() == null || contactCombo.getValue() == null || locationField.getText() == null || typeField.getText() == null || datePicker.getChronology() == null || startField.getText() == null || endField.getText() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Invalid input.");
            error.setContentText("Check values and try again.");
            error.showAndWait();
        }
        else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String strStart = datePicker.getValue().toString() + " " + startField.getText();
                LocalDateTime ts = LocalDateTime.parse(strStart, formatter);

                String strEnd = datePicker.getValue().toString() + " " + endField.getText();
                LocalDateTime te = LocalDateTime.parse(strEnd, formatter);

                if (te.isBefore(ts)) { //start and end times invalid
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error: Invalid timestamp input.");
                    error.setContentText("Appointment end time must be later than start time.");
                    error.showAndWait();
                }
                else {
                    try {
                        int contactID = DBContacts.getContactIdFromContactName(contactCombo.getValue());
                        int customerID = DBCustomer.getCustomerIdFromCustomerName(customerCombo.getValue());
                        int userID = 1;
                        Appointment newAppointment = new Appointment(
                                itemToModify.getId(),
                                titleField.getText(),
                                descriptionField.getText(),
                                locationField.getText(),
                                typeField.getText(),
                                Timestamp.valueOf(ts),
                                Timestamp.valueOf(te),
                                customerID,
                                userID,
                                contactID);
                        Appointment.updateAppointment(newAppointment, itemToModify);
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
            catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Invalid timestamp input.");
                error.setContentText("Please use 24h format 'hh:mm:ss' for appointment start and end times.");
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
