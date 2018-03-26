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
        List<String> semailList = new ArrayList<String>();
        semailList.add("141250003@qq.com");
        return studentService.subscribe("141250001@qq.com", 100000, "math", 1000, semailList);
    }
}
