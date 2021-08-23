package model;

/** Class represents an available country.*/
public class Countries {
    private int id;
    private String name;

    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() { return name; }
}
