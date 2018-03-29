package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.*;
import edu.nju.TrainingCollege.domain.*;
import edu.nju.TrainingCollege.exception.CustomException;
import edu.nju.TrainingCollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("StudentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private VerificationMapper verificationMapper;

    @Autowired
    private JavaMailSender javaMailSender;

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
        studentMapper.insertStudent(email, name, password);
        String uuid = UUID.randomUUID().toString();
        String content = "<html><body>\n" + "<a href=\"http://127.0.0.1:8080/activate?email=" + email + "&code=" + uuid + "\">激活账号</a>\n" + "</body></html>";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        try {
            message.setFrom("602485193@qq.com");
            message.setTo(email);
            message.setSubject("TrainingCollege注册验证码");
            message.setText(content);
            javaMailSender.send(mimeMessage);
            return verificationMapper.insert(email, uuid);
        } catch (MessagingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int activate(String email, String code) {
        Verification verification = verificationMapper.selectByEmail(email);
        if (verification != null)
            if (verification.getCode().equals(code))
                return studentMapper.updateLevelByEmail(1, email);
        return 0;
    }

    public int modifyMembership(String email, int level) {
        return studentMapper.updateLevelByEmail(level, email);
    }

    @Transactional
    public int modifyAccount(String email, String name, String password) {
        return studentMapper.updateNameByEmail(name, email) * studentMapper.updatePasswordByEmail(password, email);
    }

    @Transactional
    public int exchangeCredits(String email, int credit) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null)
            if (student.getPoint() > credit) {
                return studentMapper.updateDepositByEmail(student.getDeposit() + credit, email) *
                        studentMapper.updatePointByEmail(student.getPoint() - credit, email);
            }
        return 0;
    }

    public int save(String email, String password, int amount) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null)
            if (student.getPassword().equals(password))
                return studentMapper.updateDepositByEmail(student.getDeposit() + amount, email);
        return 0;
    }

    public int spend(String email, String password, int amount) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null)
            if (student.getDeposit() > amount && student.getPassword().equals(password))
                return studentMapper.updateDepositByEmail(student.getDeposit() - amount, email);
        return 0;
    }

    @Override
    @Transactional
    public int subscribe(String email, int courseid, int amount, List<String> semailList) {
        Course course = courseMapper.selectById(courseid);
        Order order = new Order(email, course.getCollege(), course.getType(), amount, 0);
        orderMapper.insertOrder(order);
        for (String semail : semailList) {
            classesMapper.insertClass(order.getId(), courseid, semail);
        }
        if ((course.getSize() - classesMapper.countByCourseid(courseid)) < semailList.size())
            throw new CustomException("No position now !");
        return 1;
    }

    @Override
    @Transactional
    public int subscribe(String email, int collegeid, String type, int amount, List<String> semailList) {
        List<Map> courseList = courseMapper.selectSuitableCourse(collegeid, type);
        int total = 0;
        int available = 0;
        for (Map map : courseList) {
            available = ((int) map.get("size") - Integer.parseInt(map.get("tempsize").toString()));
            if (available != 0) {
                total += available;
            } else {
                courseList.remove(map);
            }
        }
        if (total < semailList.size())
            return 0;

        Order order = new Order(email, collegeid, type, amount, 0);
        orderMapper.insertOrder(order);
        for (String semail : semailList) {
            for (Map map : courseList) {
                if ((int) map.get("size") > Integer.parseInt(map.get("tempsize").toString())) {
                    map.put("tempsize", (Long) map.get("tempsize") + 1);
                    classesMapper.insertClass(order.getId(), (int) map.get("id"), semail);
                    break;
                } else {
                    courseList.remove(map);
                }
            }
        }

        courseList = courseMapper.selectSuitableCourse(collegeid, type);
        for (Map map : courseList) {
            if ((int) map.get("size") < Integer.parseInt(map.get("tempsize").toString()))
                throw new CustomException("No position now !");
        }
        return 1;
    }

    @Override
    @Transactional
    public int unsubscribe(String email, String password, int orderid) {
        Order order = orderMapper.selectById(orderid);
        if (order != null) {
            College college = collegeMapper.selectById(order.getCollege());
            if (college != null) {
                if (classesMapper.deleteByOrder(orderid) * orderMapper.deleteOrderById(orderid) * save(email, password, order.getAmount()) == 0)
                    throw new CustomException("Failed to unsubscribe");
                if (collegeMapper.updateFinanceById(college.getId(), college.getFinance() - order.getAmount()) == 0)
                    throw new CustomException("Failed to unsubscribe");
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public int pay(String email, String password, int orderid) {
        Order order = orderMapper.selectById(orderid);
        if (order != null)
            if (orderMapper.updatePaytimeById(orderid) * orderMapper.updateStatusById(1, orderid) * spend(email, password, order.getAmount()) == 0)
                throw new CustomException("Failed to pay");
        orderMapper.deleteOrderByTime();
        return 1;
    }

    public Student showProfile(String email) {
        return studentMapper.selectByEmail(email);
    }

    @Override
    public List<Order> showOrders(String email) {
        return orderMapper.selectByEmail(email);
    }

    @Override
    public List<Course> showCourses(String email) {
        return courseMapper.selectBySemail(email);
    }

    @Override
    public List<Classes> showScores(String email) {
        return classesMapper.selectByEmail(email);
    }
}
