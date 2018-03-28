package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.*;
import edu.nju.TrainingCollege.domain.*;
import edu.nju.TrainingCollege.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("StaffService")
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public Staff login(String email, String password) {
        Staff staff = staffMapper.selectByEmail(email);
        if (staff != null) {
            if (staff.getPassword().equals(password)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public int register(String email, String name, String password, String title, int college) {
        return staffMapper.insertStaff(email, name, password, title, college);
    }

    @Override
    public int modifyAccount(String email, String name, String password, String title, int college) {
        return staffMapper.updateStaff(email, name, password, title, college);
    }

    @Override
    public int settleAccount(int college, String orderid) {
        return 0;
    }

    @Override
    public int approveCollege(int college, boolean decision) {
        if (decision)
            return collegeMapper.updateLevelById(college, 1);
        return 0;
    }

    @Override
    public int releaseScores(int courseid, List<Classes> transcript) {
        return 0;
    }

    @Override
    public Staff showProfile(String email) {
        return staffMapper.selectByEmail(email);
    }

    @Override
    public List<Staff> showAllStaffs() {
        return staffMapper.selectAllStaffs();
    }

    @Override
    public List<Order> showAllOrders() {
        return orderMapper.selectAllOrders();
    }

    @Override
    public List<Course> showAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public List<College> showAllColleges() {
        return collegeMapper.selectAllColleges();
    }

    @Override
    public List<Student> showAllStudents() {
        return studentMapper.selectAllStudents();
    }
}
