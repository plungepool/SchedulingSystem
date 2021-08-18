package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerScreen implements Initializable {

    public TableView<Customer> customerTable;
    public TableColumn customerTableID;
    public TableColumn customerTableName;
    public TableColumn customerTableAddress;
    public TableColumn customerTablePostalCode;
    public TableColumn customerTablePhone;
    public TableColumn customerTableDivisionID;
    public Button addButton;
    public Button modifyButton;
    public Button deleteButton;
    public Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(Customer.getAllCustomers());
        customerTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
        customerTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTableDivisionID.setCellValueFactory(new PropertyValueFactory<>("division_id"));
    }

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerAddScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyButton(ActionEvent actionEvent) throws IOException {
        try {
            CustomerModifyScreen.itemToModify = customerTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerModifyScreen.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 500, 500);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: No customer selected.");
            error.setContentText("Press ok to return.");
            error.showAndWait();
        }
    }

    public void onDeleteButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Are you sure you want to delete this customer?");
        alert.setContentText("Press ok to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            try {
                Customer itemToDelete = customerTable.getSelectionModel().getSelectedItem();

                try {
                    int customerID = itemToDelete.getId();
                    int aptID = 0;
                    String sql = "SELECT Appointment_ID FROM appointments WHERE Customer_ID = " + customerID;
                    PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        aptID = rs.getInt("Appointment_ID");
                    }

                    if (aptID != 0) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Error: Customer could not be deleted.");
                        error.setContentText("All appointments associated with this customer must be deleted first.");
                        error.showAndWait();
                    }
                    else {
                        Customer.deleteCustomer(itemToDelete);
                        customerTable.setItems(Customer.getAllCustomers());
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e ) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Customer could not be deleted.");
                error.setContentText("Make sure a customer has been selected and try again.");
                error.showAndWait();
            }
        }
    }
}
