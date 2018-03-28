package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.dao.CourseMapper;
import edu.nju.TrainingCollege.domain.Course;
import edu.nju.TrainingCollege.domain.Order;
import edu.nju.TrainingCollege.domain.Schedule;
import edu.nju.TrainingCollege.domain.Student;
import edu.nju.TrainingCollege.service.CollegeService;
import edu.nju.TrainingCollege.service.StaffService;
import edu.nju.TrainingCollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWorldController {
    @Autowired
    StudentService studentService;

    @Autowired
    StaffService staffService;

    @Autowired
    CollegeService collegeService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public int Hello(HttpServletRequest request) {
        return studentService.register("1169357116@qq.com", "yongjun", "123456");
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public int Activate(HttpServletRequest request) {
        String code = request.getParameter("code");
        String email = request.getParameter("email");
        return studentService.activate(email, code);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public int Register(HttpServletRequest request) {
        return collegeService.register("123456", "Shanghai");
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public int Subscribe(HttpServletRequest request) {
        List<String> seamilList = new ArrayList<String>();
        seamilList.add("141250003@qq.com");
        try {
            studentService.subscribe("141250001@qq.com", 1000000, "math", 2000, seamilList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    @RequestMapping(value = "/showCourses", method = RequestMethod.GET)
    public List<Course> ShowCourses(HttpServletRequest request) {
        return collegeService.showCourses(1000000);
    }

    @RequestMapping(value = "/setupCourse", method = RequestMethod.GET)
    public int setupCourse(HttpServletRequest request) {
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        Schedule schedule = new Schedule(10000, new Timestamp(2018, 3, 28, 22, 48, 0, 0), new Timestamp(2018, 3, 28, 22, 48, 0, 0));
        scheduleList.add(schedule);
        return collegeService.setupCourse(1000000, "english", "141260001@qq.com", 50, 8, 1000, scheduleList);
    }

    @RequestMapping(value = "/modifyCourse", method = RequestMethod.GET)
    public int modifyCourse(HttpServletRequest request) {
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        Schedule schedule = new Schedule(10014, new Timestamp(2018 - 1900, 3, 28, 22, 48, 0, 0), new Timestamp(2018 - 1900, 3, 28, 22, 48, 0, 0));
        scheduleList.add(schedule);
        return collegeService.modifyCourse(10014, 1000000, "english", "141260001@qq.com", 50, 8, 1000, scheduleList);
    }
}
