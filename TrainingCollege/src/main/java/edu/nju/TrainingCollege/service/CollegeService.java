package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.domain.*;

import java.util.List;

public interface CollegeService {
    public College login(int id, String password);

    public int register(String password, String location);

    public int modifyAccount(int id, String password, String location);

    public int setupCourse(int id, String type, String teacherEmail, int size, int period, int price, List<Schedule> schduleList);

    public int modifyCourse(int courseid, int id, String type, String teacherEmail, int size, int period, int price, List<Schedule> schduleList);

    public College showProfile(int id);

    public List<Staff> showStaffs(int id);

    public List<Student> showStudents(int id);

    public List<Order> showOrders(int id);

    public List<Course> showCourses(int id);
}
