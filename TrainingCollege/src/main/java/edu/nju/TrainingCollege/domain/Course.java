package edu.nju.TrainingCollege.domain;

import java.sql.Timestamp;

public class Course {
    private String id;
    private String college;
    private String type;
    private String teacherEmail;
    private Timestamp[] schedule;
    private int size;
    private int period;
    private int price;

    public Course(String id, String college, String type, String teacherEmail, Timestamp[] schedule, int size, int period, int price) {
        this.id = id;
        this.college = college;
        this.type = type;
        this.teacherEmail = teacherEmail;
        this.schedule = schedule;
        this.size = size;
        this.period = period;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public Timestamp[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Timestamp[] schedule) {
        this.schedule = schedule;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
