package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class represents an individual customer.*/
public class Customer {
    private ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String address;
    private String postalcode;
    private String phone;
    private int division_id;

    public Customer(ObservableList<Appointment> associatedAppointments, int id, String name, String address, String postalcode, String phone, int division_id) {
        this.associatedAppointments = associatedAppointments;
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

    public void addAssociatedAppointment(Appointment appointment) {
        associatedAppointments.add(appointment);
    }

    public boolean deleteAssociatedAppointment(Appointment selectedAssociatedAppointment) {
        if (associatedAppointments.contains(selectedAssociatedAppointment)) {
            associatedAppointments.remove(selectedAssociatedAppointment);
            return true;
        }
        else
            return false;
    }

    public ObservableList<Appointment> getAllAssociatedAppointments() {
        return associatedAppointments;
    }
}
