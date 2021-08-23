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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** Controller for modify appointment screen. */
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

    /** Fills forms with information from appointment to modify.*/
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

    /** Replaces appointment with modified version in database.
     * Lambda #1 on line 82 encapsulates the necessary add appointment form checks and returns a boolean.
     * Created to improve readability of line 94 conditional statement.
     * Lambda #2 on line 153 encapsulates the needed logical checks to ensure no overlaps are created in schedule and returns a boolean.
     * Created to improve readability of following conditional statement. */
    public void onSaveButton(ActionEvent actionEvent) {
        CheckValidityInterface notAllFieldsValid = () -> { //Lambda #1
            return (idField.getText() == null ||
                    titleField.getText() == null ||
                    descriptionField.getText() == null ||
                    contactCombo.getValue() == null ||
                    locationField.getText() == null ||
                    typeField.getText() == null ||
                    datePicker.getChronology() == null ||
                    startField.getText() == null ||
                    endField.getText() == null);
        };

        if (notAllFieldsValid.checkValidity()) {
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
                LocalDateTime appointmentStart = LocalDateTime.parse(strStart, formatter);
                String strEnd = datePicker.getValue().toString() + " " + endField.getText();
                LocalDateTime appointmentEnd = LocalDateTime.parse(strEnd, formatter);

                ZoneId businessZoneId = ZoneId.of("America/New_York");
                ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
                ZonedDateTime appointmentStartZDT = ZonedDateTime.of(appointmentStart, localZoneId);
                ZonedDateTime appointmentEndZDT = ZonedDateTime.of(appointmentEnd, localZoneId);

                LocalTime openingBusinessHours = LocalTime.of(8, 0, 0);
                LocalTime closingBusinessHours = LocalTime.of(22, 0, 0);
                ZonedDateTime openingBusinessHoursZDT = ZonedDateTime.of(appointmentStartZDT.toLocalDate(), openingBusinessHours, businessZoneId);
                ZonedDateTime closingBusinessHoursZDT = ZonedDateTime.of(appointmentEndZDT.toLocalDate(), closingBusinessHours, businessZoneId);

                if (appointmentEnd.isBefore(appointmentStart)) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error: Invalid timestamp input.");
                    error.setContentText("Appointment end time must be later than start time.");
                    error.showAndWait();
                }
                else if (appointmentStartZDT.isBefore(openingBusinessHoursZDT) || appointmentEndZDT.isAfter(closingBusinessHoursZDT)) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error: Appointment outside of business hours.");
                    error.setContentText("Please schedule between 8am and 10pm EST.");
                    error.showAndWait();
                }
                else {
                    int contactID = DBContacts.getContactIdFromContactName(contactCombo.getValue());
                    int customerID = DBCustomer.getCustomerIdFromCustomerName(customerCombo.getValue());
                    int userID = 1;
                    Timestamp appointmentStartUTC = Timestamp.valueOf(appointmentStart.plusSeconds(-1 * appointmentStartZDT.getOffset().getTotalSeconds()));
                    Timestamp appointmentEndUTC = Timestamp.valueOf(appointmentEnd.plusSeconds(-1 * appointmentEndZDT.getOffset().getTotalSeconds()));

                    boolean overlapFlag = false;

                    ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
                    customerAppointments.addAll(Appointment.getAllAppointmentsByCustomer(customerID));
                    LocalDateTime newAppointmentStart = appointmentStartZDT.toLocalDateTime();
                    LocalDateTime newAppointmentEnd = appointmentEndZDT.toLocalDateTime();
                    for (Appointment a : customerAppointments) {
                        LocalDateTime thisAppointmentStart = a.getStart().toLocalDateTime();
                        LocalDateTime thisAppointmentEnd = a.getEnd().toLocalDateTime();

                        CheckValidityInterface appointmentsOverlap = () -> { //Lambda #2
                            return (newAppointmentStart.isAfter(thisAppointmentStart) && newAppointmentStart.isBefore(thisAppointmentEnd))
                                    || (newAppointmentEnd.isAfter(thisAppointmentStart) && newAppointmentEnd.isBefore(thisAppointmentEnd))
                                    || (newAppointmentStart.isAfter(thisAppointmentStart) && newAppointmentEnd.isBefore(thisAppointmentEnd))
                                    || (thisAppointmentStart.isAfter(newAppointmentStart) && thisAppointmentEnd.isBefore(newAppointmentEnd))
                                    || newAppointmentStart.equals(thisAppointmentStart) || newAppointmentEnd.equals(thisAppointmentEnd)
                                    || newAppointmentStart.equals(thisAppointmentEnd) || newAppointmentEnd.equals(thisAppointmentStart);
                        };

                        if (a.getId() != itemToModify.getId()) {
                            if(appointmentsOverlap.checkValidity()){
                                overlapFlag = true;
                                Alert error = new Alert(Alert.AlertType.ERROR);
                                error.setTitle("Error");
                                error.setHeaderText("Error: Appointment overlaps an existing appointment for this customer.");
                                error.setContentText("Please check schedule and try again.");
                                error.showAndWait();
                                break;
                            }
                        }
                    }
                    if (!overlapFlag) {
                        try {
                            Appointment newAppointment = new Appointment(
                                    0,
                                    titleField.getText(),
                                    descriptionField.getText(),
                                    locationField.getText(),
                                    typeField.getText(),
                                    appointmentStartUTC,
                                    appointmentEndUTC,
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

    /** Transitions to main screen.*/
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CalendarScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }
}
