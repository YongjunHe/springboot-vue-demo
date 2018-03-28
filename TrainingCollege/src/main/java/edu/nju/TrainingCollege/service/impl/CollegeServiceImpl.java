package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.*;
import edu.nju.TrainingCollege.domain.*;
import edu.nju.TrainingCollege.service.CollegeService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service("CollegeService")
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public College login(int id, String password) {
        College college = collegeMapper.selectById(id);
        if (college != null) {
            if (college.getPassword().equals(password)) {
                return college;
            }
        }
        return null;
    }

    @Override
    public int register(String password, String location) {
        College college = new College(password, location);
        if (collegeMapper.insertCollege(college) != 0)
            return college.getId();
        return 0;
    }

    @Override
    public int modifyAccount(int id, String password, String location) {
        return collegeMapper.updatePasswordById(id, password) * collegeMapper.updateLocationById(id, location);
    }

    @Override
    public int setupCourse(int id, String type, String teacherEmail, int size, int period, int price, List<Schedule> schduleList) {
        Course course = new Course(id, type, teacherEmail, size, period, price, schduleList);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            courseMapper.insertCourse(course);
            for (Schedule schedule : schduleList) {
                schedule.setCourseid(course.getId());
                scheduleMapper.insertSchedule(schedule);
            }
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception ex) {
            platformTransactionManager.rollback(transactionStatus);
            return 0;
        }
        return course.getId();
    }

    @Override
    public int modifyCourse(int courseid, int id, String type, String teacherEmail, int size, int period, int price, List<Schedule> schduleList) {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            courseMapper.updateCourse(courseid, id, type, teacherEmail, size, period, price);
            for (Schedule schedule : schduleList)
                scheduleMapper.updateSchedule(schedule);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception ex) {
            platformTransactionManager.rollback(transactionStatus);
            return 0;
        }
        return 1;
    }

    @Override
    public College showProfile(int id) {
        return collegeMapper.selectById(id);
    }

    @Override
    public List<Staff> showStaffs(int id) {
        return staffMapper.selectByCollege(id);
    }

    @Override
    public List<Student> showStudents(int id) {
        return studentMapper.selectByCollege(id);
    }

    @Override
    public List<Order> showOrders(int id) {
        return orderMapper.selectByCollege(id);
    }

    @Override
    public List<Course> showCourses(int id) {
        return courseMapper.selectByCollege(id);
    }
}
