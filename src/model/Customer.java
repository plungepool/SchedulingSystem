package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class represents an individual customer.*/
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalcode;
    private String phone;
    private int division_id;

    public Customer(int id, String name, String address, String postalcode, String phone, int division_id) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalcode = postalcode;
        this.phone = phone;
        this.division_id = division_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivision_id() {
        return division_id;
    }

    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalcode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division_id = rs.getInt("Division_ID");

                Customer c = new Customer(id, name, address, postalcode, phone, division_id);
                customersList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    public static void addNewCustomer(Customer newCustomer){
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                    "VALUES (" + newCustomer.getId() + ", '" + newCustomer.getName() + "', '" + newCustomer.getAddress() + "', '" +
                    newCustomer.getPostalcode() + "', '" + newCustomer.getPhone() + "', " + newCustomer.getDivision_id() + ")";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customer itemToUpdate) {

    }

    public static void deleteCustomer(Customer itemToDelete) {
        try {
            int customerIdToDelete = itemToDelete.getId();
            String sql = "DELETE FROM customers WHERE Customer_ID = " + customerIdToDelete;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement((sql));
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
