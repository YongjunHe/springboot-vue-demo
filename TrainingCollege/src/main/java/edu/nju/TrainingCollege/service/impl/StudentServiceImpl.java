package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.ClassesMapper;
import edu.nju.TrainingCollege.dao.CourseMapper;
import edu.nju.TrainingCollege.dao.OrderMapper;
import edu.nju.TrainingCollege.dao.StudentMapper;
import edu.nju.TrainingCollege.domain.Course;
import edu.nju.TrainingCollege.domain.Order;
import edu.nju.TrainingCollege.domain.Student;
import edu.nju.TrainingCollege.service.StudentService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("StudentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ClassesMapper classesMapper;

    @Autowired
    SqlSession sqlSession;

    public Student login(String email, String password) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null) {
            if (student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    public int register(String email, String name, String password) {
        return studentMapper.insertStudent(email, name, password);
    }

    public int modifyMembership(String email, int level) {
        return studentMapper.updateLevelByEmail(level, email);
    }

    @Transactional
    public int modifyAccount(String email, String name, String password) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null) {
            studentMapper.updateNameByEmail(name, email);
            studentMapper.updatePasswordByEmail(password, email);
            return 1;
        }
        return 0;
    }

    @Transactional
    public int exchangeCredits(String email, int credit) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null) {
            if (student.getPoint() > credit) {
                studentMapper.updateDepositByEmail(student.getDeposit() + credit, email);
                studentMapper.updatePointByEmail(student.getPoint() - credit, email);
                return 1;
            }
        }
        return 0;
    }

    public int getDiscount(String email) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null) {
            return student.getLevel();
        }
        return 0;
    }

    @Transactional
    public int save(String email, int amount) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null) {
            studentMapper.updateDepositByEmail(student.getDeposit() + amount, email);
            return 1;
        }
        return 0;
    }

    @Transactional
    public int spend(String email, int amount) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null) {
            if (student.getDeposit() > amount) {
                studentMapper.updateDepositByEmail(student.getDeposit() + amount, email);
                return 1;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int subscribe(String email, int courseid, int amount, List<String> semailList) {
        Course course = courseMapper.selectById(courseid);
        orderMapper.insertOrder(email, course.getCollege(), course.getType(), amount);
        int orderid = orderMapper.selectMaxId();
        if ((course.getSize() - classesMapper.selectByCourseid(courseid)) < semailList.size())
            return 0;
        try {
            sqlSession.getConnection().setAutoCommit(false);
            sqlSession.getConfiguration().setDefaultExecutorType(ExecutorType.BATCH);
            for (String semail : semailList) {
                classesMapper.insertClass(orderid, courseid, semail);
            }
            sqlSession.commit();
        } catch (Exception ex) {
            sqlSession.rollback();
            return 0;
        }
        return 1;
    }

    @Override
    @Transactional
    public int subscribe(String email, int collegeid, String type, int amount, List<String> semailList) {
        orderMapper.insertOrder(email, collegeid, type, amount);
        int orderid = orderMapper.selectMaxId();
        List<Map> courseList = courseMapper.selectAvailableCourse(collegeid, type);
        int available = 0;
        for (Map map : courseList)
            available += ((int) map.get("size") - (int) map.get("tempsize"));
        if (available < semailList.size())
            return 0;
        for (String semail : semailList) {
            for (Map map : courseList) {
                if ((int) map.get("tempsize") < (int) map.get("size")) {
                    map.put("tempsize", (int) map.get("tempsize") + 1);
                    classesMapper.insertClass(orderid, (int) map.get("id"), semail);
                    break;
                }
            }
        }
        return 1;
    }

    @Override
    public int unsubscribe(String orderid) {
        return 0;
    }

    @Override
    public List<Order> showOrders(String email) {
        return orderMapper.selectByEmail(email);
    }

    @Override
    public List<Course> showCourses(String email) {
        return courseMapper.selectBySemail(email);
    }
}
