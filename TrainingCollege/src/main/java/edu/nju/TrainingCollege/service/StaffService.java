package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.domain.*;

import java.util.List;
import java.util.Map;

public interface StaffService {
    public Staff login(String email, String password);

    public int register(String email, String name, String password, String title, int college);

    public int modifyAccount(String email, String name, String password, String title, int college);

    public int settleAccount(int college);

    public int approveCollege(int college, boolean decision);

    public int releaseScores(List<Classes> transcript);

    public Staff showProfile(String email);

    public List<Classes> showScores(String email);

    public List<Staff> showAllStaffs();

    public List<Order> showAllOrders();

    public List<Course> showAllCourses();

    public List<College> showAllColleges();

    public List<Student> showAllStudents();
}
