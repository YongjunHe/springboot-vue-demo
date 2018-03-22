package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.domain.Student;

public interface StudentService {
    public Student login(String email, String password);

    public int register(String email, String name, String password);

    public int modifyAccount(String email, String name, String password);

    public int modifyMembership(String email, int level);

    public int exchangeCredits(String email, int credit);

    public int getDiscount(String email);

    public int save(String email, int amount);

    public int spend(String email, int amount);

    public int subscribe(String email, String courseid);

    public int unsubscribe(String email, String courseid);

    public int showOrders(String email);

    public int showCourses(String email);
}
