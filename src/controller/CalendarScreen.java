package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

    public void monthlyFilterTable() {
        if (monthlyCombo.getValue() != null) {
            String chosenMonth = monthlyCombo.getValue();
            monthlyAppointmentsTable.setItems(Appointment.filterByMonth(chosenMonth));
        }
    }

    public void weeklyFilterTable() {
        if (weeklyCombo.getValue() != null) {
            String chosenWeek = weeklyCombo.getValue();
            weeklyAppointmentsTable.setItems(Appointment.filterByWeek(chosenWeek));
        }
    }

    public void onTabSwitchToMonthly() {
        monthlyAppointmentsTable.getSelectionModel().clearSelection();
    }

    public void onTabSwitchToWeekly() {
        weeklyAppointmentsTable.getSelectionModel().clearSelection();
    }

    public void toAddAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CalendarAddScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyButton(ActionEvent actionEvent) {
//        try {
//            CustomerModifyScreen.itemToModify = (Customer) customerTable.getSelectionModel().getSelectedItem();
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerModifyScreen.fxml")));
//            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root, 500, 500);
//            stage.setTitle("Modify Customer");
//            stage.setScene(scene);
//            stage.show();
//        }
//        catch (Exception e) {
//            Alert error = new Alert(Alert.AlertType.ERROR);
//            error.setTitle("Error");
//            error.setHeaderText("Error: No customer selected.");
//            error.setContentText("Press ok to return.");
//            error.showAndWait();
//        }
    }

    public void onDeleteButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("Press ok to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            try {
                if (monthlyAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                    Appointment itemToDelete = monthlyAppointmentsTable.getSelectionModel().getSelectedItem();
                    Appointment.deleteAppointment(itemToDelete);
                }
                else if (weeklyAppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                    Appointment itemToDelete = weeklyAppointmentsTable.getSelectionModel().getSelectedItem();
                    Appointment.deleteAppointment(itemToDelete);
                }
                monthlyAppointmentsTable.setItems(Appointment.getAllAppointments());
                monthlyFilterTable();
                weeklyAppointmentsTable.setItems(Appointment.getAllAppointments());
                weeklyFilterTable();
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
