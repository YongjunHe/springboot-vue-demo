package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.domain.Classes;
import edu.nju.TrainingCollege.domain.Course;
import edu.nju.TrainingCollege.domain.Order;
import edu.nju.TrainingCollege.domain.Student;

import java.util.List;

public interface StudentService {
    public Student login(String email, String password);

    public int register(String email, String name, String password);

    public int activate(String email, String code);

    public int modifyAccount(String email, String name, String password);

    public int modifyMembership(String email, int level);

    public int exchangeCredits(String email, int credit);

    public int save(String email, int amount);

    public int spend(String email, int amount);

    public int subscribe(String email, int courseid, int amount, List<Student> studentList);

    public int subscribe(String email, int collegeid, String type, int amount, List<Student> studentList);

    public int unsubscribe(String email, int orderid);

    public int pay(String email, int orderid);

    public Student showProfile(String email);

    public List<Order> showOrders(String email);

    public List<Course> showCourses(String email);

    public List<Classes> showScores(String email);
}
