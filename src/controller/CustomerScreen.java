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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerScreen implements Initializable {

    public TableView customerTable;
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
            CustomerModifyScreen.itemToModify = (Customer) customerTable.getSelectionModel().getSelectedItem();
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
                Customer itemToDelete = (Customer) customerTable.getSelectionModel().getSelectedItem();
                Customer.deleteCustomer(itemToDelete);
                customerTable.setItems(Customer.getAllCustomers());
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

//    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
//        try {
//            ModifyPartScreen.itemToModify = PartsTable.getSelectionModel().getSelectedItem();
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPartScreen.fxml")));
//            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root, 600, 400);
//            stage.setTitle("Modify Part");
//            stage.setScene(scene);
//            stage.show();
//        }
//        catch (Exception e) {
//            Alert error = new Alert(Alert.AlertType.ERROR);
//            error.setTitle("Error");
//            error.setHeaderText("Error: No part selected.");
//            error.setContentText("Press ok to return.");
//            error.showAndWait();
//        }
//    }

}
