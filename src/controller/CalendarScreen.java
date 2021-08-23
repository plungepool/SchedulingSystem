package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for calendar screen. */
public class CalendarScreen implements Initializable {
    public TableView<Appointment> monthlyAppointmentsTable;
    public TableColumn<Object, Object> monthlyAppointmentsTableID;
    public TableColumn<Object, Object> monthlyAppointmentsTableTitle;
    public TableColumn<Object, Object> monthlyAppointmentsTableDescription;
    public TableColumn<Object, Object> monthlyAppointmentsTableLocation;
    public TableColumn<Object, Object> monthlyAppointmentsTableContact;
    public TableColumn<Object, Object> monthlyAppointmentsTableType;
    public TableColumn<Object, Object> monthlyAppointmentsTableStart;
    public TableColumn<Object, Object> monthlyAppointmentsTableEnd;
    public TableColumn<Object, Object> monthlyAppointmentsTableCustomerID;

    public TableView<Appointment> weeklyAppointmentsTable;
    public TableColumn<Object, Object> weeklyAppointmentsTableID;
    public TableColumn<Object, Object> weeklyAppointmentsTableTitle;
    public TableColumn<Object, Object> weeklyAppointmentsTableDescription;
    public TableColumn<Object, Object> weeklyAppointmentsTableLocation;
    public TableColumn<Object, Object> weeklyAppointmentsTableContact;
    public TableColumn<Object, Object> weeklyAppointmentsTableType;
    public TableColumn<Object, Object> weeklyAppointmentsTableStart;
    public TableColumn<Object, Object> weeklyAppointmentsTableEnd;
    public TableColumn<Object, Object> weeklyAppointmentsTableCustomerID;

    public ComboBox<String> monthlyCombo;
    ObservableList<String> months = FXCollections.observableArrayList();

    public ComboBox<String> weeklyCombo;
    ObservableList<String> weeks = FXCollections.observableArrayList();

    /** Fills tableviews with appointments and sets comboboxes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthlyAppointmentsTable.setItems(Appointment.getAllAppointments());
        monthlyAppointmentsTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        monthlyAppointmentsTableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthlyAppointmentsTableDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthlyAppointmentsTableLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthlyAppointmentsTableContact.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
        monthlyAppointmentsTableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthlyAppointmentsTableStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        monthlyAppointmentsTableEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        monthlyAppointmentsTableCustomerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        months.addAll("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
        monthlyCombo.setItems(months);

        weeklyAppointmentsTable.setItems(Appointment.getAllAppointments());
        weeklyAppointmentsTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        weeklyAppointmentsTableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        weeklyAppointmentsTableDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeklyAppointmentsTableLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        weeklyAppointmentsTableContact.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
        weeklyAppointmentsTableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        weeklyAppointmentsTableStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        weeklyAppointmentsTableEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        weeklyAppointmentsTableCustomerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        weeks.addAll("THIS WEEK", "NEXT WEEK");
        weeklyCombo.setItems(weeks);
    }

    /** Transitions to main screen.*/
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

    /** Filters appointments by month.*/
    public void monthlyFilterTable() {
        if (monthlyCombo.getValue() != null) {
            String chosenMonth = monthlyCombo.getValue();
            monthlyAppointmentsTable.setItems(Appointment.filterByMonth(chosenMonth));
        }
    }

    /** Filters appointments by week.*/
    public void weeklyFilterTable() {
        if (weeklyCombo.getValue() != null) {
            String chosenWeek = weeklyCombo.getValue();
            weeklyAppointmentsTable.setItems(Appointment.filterByWeek(chosenWeek));
        }
    }

    /** Actions when monthly tab is selected.*/
    public void onTabSwitchToMonthly() {
        monthlyAppointmentsTable.getSelectionModel().clearSelection();
    }

    /** Actions when weekly tab is selected.*/
    public void onTabSwitchToWeekly() {
        weeklyAppointmentsTable.getSelectionModel().clearSelection();
    }

    /** Transitions to add appointment screen.*/
    public void toAddAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CalendarAddScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /** Transitions to modify appointment screen.*/
    public void onModifyButton(ActionEvent actionEvent) {
        try {
            if (monthlyAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                CalendarModifyScreen.itemToModify = monthlyAppointmentsTable.getSelectionModel().getSelectedItem();
            }
            else if (weeklyAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                CalendarModifyScreen.itemToModify = weeklyAppointmentsTable.getSelectionModel().getSelectedItem();
            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CalendarModifyScreen.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 500, 500);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: No appointment selected.");
            error.setContentText("Press ok to return.");
            error.showAndWait();
        }
    }

    /** Deletes selected appointment from database.*/
    public void onDeleteButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("Press ok to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            try {
                String aptType = "";
                String aptId = "";
                if (monthlyAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                    Appointment itemToDelete = monthlyAppointmentsTable.getSelectionModel().getSelectedItem();
                    aptType = itemToDelete.getType();
                    aptId = String.valueOf(itemToDelete.getId());
                    Appointment.deleteAppointment(itemToDelete);
                }
                else if (weeklyAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                    Appointment itemToDelete = weeklyAppointmentsTable.getSelectionModel().getSelectedItem();
                    aptType = itemToDelete.getType();
                    aptId = String.valueOf(itemToDelete.getId());
                    Appointment.deleteAppointment(itemToDelete);
                }
                monthlyAppointmentsTable.setItems(Appointment.getAllAppointments());
                monthlyFilterTable();
                weeklyAppointmentsTable.setItems(Appointment.getAllAppointments());
                weeklyFilterTable();

                Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                deleted.setTitle("Success");
                deleted.setHeaderText(aptType + " appointment ID #" + aptId + " has been deleted.");
                deleted.setContentText("Press ok to continue.");
                deleted.showAndWait();
            }
            catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Customer could not be deleted.");
                error.setContentText("Make sure a customer has been selected and try again.");
                error.showAndWait();
            }
        }
    }
}
