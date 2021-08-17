package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

    public void monthlyFilterTable(ActionEvent actionEvent) {
        String chosenMonth = monthlyCombo.getValue();
        monthlyAppointmentsTable.setItems(Appointment.filterByMonth(chosenMonth));
    }

    public void weeklyFilterTable(ActionEvent actionEvent) {
    }
}
