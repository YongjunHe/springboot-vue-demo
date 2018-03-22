package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.domain.Staff;

import java.util.Map;

public interface StaffService {
    public Staff login(String email, String password);

    public int register(String email, String name, String password, String title, String college);

    public int modifyAccount(String email, String name, String password, String title, String college);

    public int settleAccount(String collegeid, String orderid);

    public int approveCollege(String collegeid, boolean decision);

    public int releaseScores(String courseid, Map<String, Integer> transcript);

    public int showAllStudents();

    public int showAllOrders();

    public int showAllCourses();
}
