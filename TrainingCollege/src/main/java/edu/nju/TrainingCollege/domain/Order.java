package edu.nju.TrainingCollege.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class Order {
    private int id;
    private String email;
    private int college;
    private String type;
    private int amount;
    private int status;
    private Timestamp creattime;
    private Timestamp paytime;
    private Timestamp settletime;
    private List<Student> studentList;

    public Order() {

    }

    public Order(String email, int college, String type, int amount, int status) {
        this.email = email;
        this.college = college;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreattime() {
        return creattime;
    }

    public void setCreattime(Timestamp creattime) {
        this.creattime = creattime;
    }

    public Timestamp getPaytime() {
        return paytime;
    }

    public void setPaytime(Timestamp paytime) {
        this.paytime = paytime;
    }

    public Timestamp getSettletime() {
        return settletime;
    }

    public void setSettletime(Timestamp settletime) {
        this.settletime = settletime;
    }
}
