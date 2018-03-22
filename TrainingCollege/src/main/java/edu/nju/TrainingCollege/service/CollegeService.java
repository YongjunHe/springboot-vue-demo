package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.domain.College;

import java.sql.Timestamp;

public interface CollegeService {
    public College login(String id, String password);

    public int register(String id, String password, String location);

    public int modifyAccount(String id, String password, String location);

    public int setupCourse(String id, String courseid, String type, String teacherEmail, Timestamp[] schdule, int size, int period, int price);

    public int modifyCourse(String id, String courseid, String type, String teacherEmail, Timestamp[] schdule, int size, int period, int price);

    public int showStudents(String id);

    public int showOrders(String id);

    public int showCourses(String id);
}
