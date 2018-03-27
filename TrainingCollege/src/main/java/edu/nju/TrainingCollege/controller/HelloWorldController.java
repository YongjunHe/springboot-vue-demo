package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.Course;
import edu.nju.TrainingCollege.domain.Order;
import edu.nju.TrainingCollege.domain.Student;
import edu.nju.TrainingCollege.service.StaffService;
import edu.nju.TrainingCollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWorldController {
    @Autowired
    StudentService studentService;

    @Autowired
    StaffService staffService;

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
}
