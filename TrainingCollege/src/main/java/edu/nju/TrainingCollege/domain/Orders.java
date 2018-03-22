package edu.nju.TrainingCollege.domain;

import java.util.Map;

public class Orders {
    private String id;
    private String email;
    private Map<String, String> students;
    private String type;
    private int amount;

    public Orders(String id, String email, Map<String, String> students, String type, int amount) {
        this.id = id;
        this.email = email;
        this.students = students;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getStudents() {
        return students;
    }

    public void setStudents(Map<String, String> students) {
        this.students = students;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
