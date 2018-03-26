package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.StudentMapper;
import edu.nju.TrainingCollege.domain.Staff;
import edu.nju.TrainingCollege.domain.Student;
import edu.nju.TrainingCollege.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StaffService")
public class StaffServiceImpl implements StaffService{
    @Autowired
    StudentMapper studentMapper;

    @Override
    public Staff login(String email, String password) {
        return null;
    }

    @Override
    public int register(String email, String name, String password, String title, String college) {
        return 0;
    }

    @Override
    public int modifyAccount(String email, String name, String password, String title, String college) {
        return 0;
    }

    @Override
    public int settleAccount(String collegeid, String orderid) {
        return 0;
    }

    @Override
    public int approveCollege(String collegeid, boolean decision) {
        return 0;
    }

    @Override
    public int releaseScores(String courseid, Map<String, Integer> transcript) {
        return 0;
    }

    @Override
    public List<Student> showAllStudents() {
        return studentMapper.selectAllStudents();
    }

    @Override
    public int showAllOrders() {
        return 0;
    }

    @Override
    public int showAllCourses() {
        return 0;
    }
}
