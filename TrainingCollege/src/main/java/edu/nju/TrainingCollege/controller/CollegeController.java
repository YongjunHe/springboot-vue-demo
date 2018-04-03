package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.*;
import edu.nju.TrainingCollege.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @CrossOrigin
    @RequestMapping(value = "/college/login", method = RequestMethod.GET)
    public College login(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        String password = request.getParameter("password");
        HttpSession httpSession = request.getSession();
        College college = collegeService.login(collegeId, password);
        if (college != null) {
            httpSession.setAttribute("userId", collegeId);
            httpSession.setAttribute("userType", "college");
        }
        return college;
    }

    @CrossOrigin
    @RequestMapping(value = "/college/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("userId");
        httpSession.removeAttribute("userType");
    }

    @RequestMapping(value = "/college/register", method = RequestMethod.GET)
    public int register(HttpServletRequest request) {
        String password = request.getParameter("password");
        String location = request.getParameter("location");
        return collegeService.register(password, location);
    }

    @RequestMapping(value = "/college/profile", method = RequestMethod.GET)
    public College profile(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("userId"));
        return collegeService.showProfile(collegeId);
    }

    @RequestMapping(value = "/college/modifyAccount", method = RequestMethod.GET)
    public int modifyAccount(HttpServletRequest request) {
        String location = request.getParameter("location");
        String password = request.getParameter("password");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return collegeService.modifyAccount(collegeId, password, location);
    }

    @RequestMapping(value = "/college/showStaffs", method = RequestMethod.GET)
    public List<Staff> showStaffs(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return collegeService.showStaffs(collegeId);
    }

    @RequestMapping(value = "/college/showStudents", method = RequestMethod.GET)
    public List<Student> showStudents(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return collegeService.showStudents(collegeId);
    }

    @RequestMapping(value = "/college/showCourses", method = RequestMethod.GET)
    public List<Course> showCourses(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return collegeService.showCourses(collegeId);
    }

    @RequestMapping(value = "/college/showOrders", method = RequestMethod.GET)
    public List<Order> showOrders(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return collegeService.showOrders(collegeId);
    }

    @RequestMapping(value = "/college/setupCourse", method = RequestMethod.POST)
    @ResponseBody
    public int setupCourse(@RequestBody Course course) {
        return collegeService.setupCourse(course.getCollege(), course.getType(), course.getTemail(), course.getSize(), course.getPeriod(), course.getPrice(), course.getScheduleList());
    }

    @RequestMapping(value = "/college/modifyCourse", method = RequestMethod.POST)
    @ResponseBody
    public int modifyCourse(@RequestBody Course course) {
        System.out.println(course.getId());
        System.out.println(course.getCollege());
        return collegeService.modifyCourse(course.getId(), course.getCollege(), course.getType(), course.getTemail(), course.getSize(), course.getPeriod(), course.getPrice(), course.getScheduleList());
    }
}
