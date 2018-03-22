package edu.nju.TrainingCollege.domain;

public class College {
    private String id;
    private String password;
    private String location;
    private int finance;

    public College(String id, String password, String location, int finance) {
        this.id = id;
        this.password = password;
        this.location = location;
        this.finance = finance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFinance() {
        return finance;
    }

    public void setFinance(int finance) {
        this.finance = finance;
    }
}
