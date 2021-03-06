package model;

/** This class represents a business contact.*/
public class Contacts {
    private int id;
    private String name;
    private String email;

    public Contacts(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }
}

