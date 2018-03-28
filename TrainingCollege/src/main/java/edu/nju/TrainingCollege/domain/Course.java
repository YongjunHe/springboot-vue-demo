package edu.nju.TrainingCollege.domain;

import java.sql.Timestamp;
import java.util.List;

public class Course {
    private int id;
    private int college;
    private String type;
    private String temail;
    private int size;
    private int period;
    private int price;
    private List<Schedule> scheduleList;
    private List<Student> studentList;

    public Course() {

    }

    public Course(int college, String type, String temail, int size, int period, int price, List<Schedule> scheduleList) {
        this.college = college;
        this.type = type;
        this.temail = temail;
        this.size = size;
        this.period = period;
        this.price = price;
        this.scheduleList = scheduleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
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

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
