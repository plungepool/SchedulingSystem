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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;
import utils.DBCountries;
import utils.DBFirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static utils.DBFirstLevelDivisions.getDivisionIdFromDivisionName;

/** Controller for add customers screen. */
public class CustomerAddScreen implements Initializable {

    public TextField idField;
    public TextField nameField;
    public TextField postalField;
    public TextField phoneField;
    public TextField addressField;
    public ComboBox<String> countryCombo;
    public ComboBox<String> divisionCombo;

    /** Fills comboboxes with items from database.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        for (Countries c : DBCountries.getAllCountries()) {
            countryNames.add(c.getName());
        }
        countryCombo.setItems(countryNames);
    }

    /** Populates division comboboxes based on country selection.*/
    public void onCountrySelect(ActionEvent actionEvent) {
        divisionCombo.getItems().clear();
        int selectedCountryId;
        if (countryCombo.getValue().equals("U.S")) {
            selectedCountryId = 1;
        }
        else if (countryCombo.getValue().equals("UK")) {
            selectedCountryId = 2;
        }
        else if (countryCombo.getValue().equals("Canada")) {
            selectedCountryId = 3;
        }
        else {
            selectedCountryId = 0;
        }

        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (FirstLevelDivisions f : DBFirstLevelDivisions.getAllDivisionsFromCountryID(selectedCountryId)) {
            divisionNames.add(f.getName());
        }
        divisionCombo.setItems(divisionNames);
    }

    /** Creates new customer and adds to database.*/
    public void onSaveButton(ActionEvent actionEvent) {
        if (nameField.getText() == null || addressField.getText() == null || postalField.getText() == null || phoneField.getText() == null || divisionCombo.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Invalid input.");
            error.setContentText("Check values and try again.");
            error.showAndWait();
        }
        else {
            try {
                int divisionID = getDivisionIdFromDivisionName(divisionCombo.getValue().toString());
                Customer newCustomer = new Customer(
                        0,
                        nameField.getText(),
                        addressField.getText(),
                        postalField.getText(),
                        phoneField.getText(),
                        divisionID);
                Customer.addNewCustomer(newCustomer);
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

    /** Transitions to main screen.*/
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerScreen.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("SchedulingSystem");
        stage.setScene(scene);
        stage.show();
    }

}
