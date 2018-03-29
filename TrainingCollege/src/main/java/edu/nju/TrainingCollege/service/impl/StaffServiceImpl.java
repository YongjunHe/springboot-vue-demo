package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.*;
import edu.nju.TrainingCollege.domain.*;
import edu.nju.TrainingCollege.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    private ClassesMapper classesMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

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
    public int settleAccount(int college) {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            int totalamount = collegeMapper.selectById(college).getFinance();
            List<Order> orderList = orderMapper.selectByCollege(college);
            for (Order order : orderList) {
                if (order.getStatus() == 1) {
                    orderMapper.updateStatusById(2, order.getId());
                    totalamount += order.getAmount();
                }
            }
            collegeMapper.updateFinanceById(college, totalamount);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception ex) {
            platformTransactionManager.rollback(transactionStatus);
            return 0;
        }
        return 1;
    }

    @Override
    public int approveCollege(int college, boolean decision) {
        if (decision)
            return collegeMapper.updateLevelById(college, 1);
        return 0;
    }

    @Override
    public int releaseScores(int courseid, List<Classes> transcript) {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            for (Classes classes : transcript)
                classesMapper.updateScore(classes.getScore(), classes.getCourseid(), classes.getSemail());
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception ex) {
            platformTransactionManager.rollback(transactionStatus);
            return 0;
        }
        return 1;
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
