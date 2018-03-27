package edu.nju.TrainingCollege.service.impl;

import com.sun.net.httpserver.Authenticator;
import edu.nju.TrainingCollege.dao.*;
import edu.nju.TrainingCollege.domain.Verification;
import edu.nju.TrainingCollege.exception.CustomException;
import edu.nju.TrainingCollege.domain.Course;
import edu.nju.TrainingCollege.domain.Order;
import edu.nju.TrainingCollege.domain.Student;
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
import java.util.concurrent.TimeUnit;

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
    VerificationMapper verificationMapper;

    @Autowired
    JavaMailSender javaMailSender;

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
        if (student != null)
            if (student.getPoint() > credit) {
                studentMapper.updateDepositByEmail(student.getDeposit() + credit, email);
                studentMapper.updatePointByEmail(student.getPoint() - credit, email);
                return 1;
            }
        return 0;
    }

    public int getDiscount(String email) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null)
            return student.getLevel();
        return 0;
    }

    public int save(String email, int amount) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null)
            return studentMapper.updateDepositByEmail(student.getDeposit() + amount, email);
        return 0;
    }

    public int spend(String email, int amount) {
        Student student = studentMapper.selectByEmail(email);
        if (student != null)
            if (student.getDeposit() > amount)
                return studentMapper.updateDepositByEmail(student.getDeposit() + amount, email);
        return 0;
    }

    @Override
    @Transactional
    public int subscribe(String email, int courseid, int amount, List<String> semailList) {
        Course course = courseMapper.selectById(courseid);
        orderMapper.insertOrder(email, course.getCollege(), course.getType(), amount);
        int orderid = orderMapper.selectLastId();
        for (String semail : semailList) {
            classesMapper.insertClass(orderid, courseid, semail);
        }
        if ((course.getSize() - classesMapper.selectByCourseid(courseid)) < semailList.size())
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

        orderMapper.insertOrder(email, collegeid, type, amount);
        int orderid = orderMapper.selectLastId();
        for (String semail : semailList) {
            for (Map map : courseList) {
                if ((int) map.get("size") > Integer.parseInt(map.get("tempsize").toString())) {
                    map.put("tempsize", (Long) map.get("tempsize") + 1);
                    classesMapper.insertClass(orderid, (int) map.get("id"), semail);
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
    public int unsubscribe(int orderid) {
        orderMapper.deleteOrder(orderid);
        classesMapper.deleteByOrder(orderid);
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
