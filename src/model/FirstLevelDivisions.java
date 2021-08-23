package model;

/** Class representing First Level Divisions of each available country.*/
public class FirstLevelDivisions {
    private int id;
    private String name;
    private int country_id;

    public FirstLevelDivisions(int id, String name, int country_id) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getCountry_id() { return country_id; }
}
