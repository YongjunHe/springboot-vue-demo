package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.*;
import edu.nju.TrainingCollege.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class StaffController {
    @Autowired
    StaffService staffService;

    @CrossOrigin
    @RequestMapping(value = "/staff/login", method = RequestMethod.GET)
    public Staff login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession httpSession = request.getSession();
        Staff staff = staffService.login(email, password);
        if (staff != null) {
            httpSession.setAttribute("userId", email);
            httpSession.setAttribute("userType", "staff");
        }
        return staff;
    }

    @CrossOrigin
    @RequestMapping(value = "/staff/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("userId");
        httpSession.removeAttribute("userType");
    }

    @RequestMapping(value = "/staff/register", method = RequestMethod.GET)
    public int register(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return staffService.register(email, name, password, title, collegeId);
    }

    @RequestMapping(value = "/staff/profile", method = RequestMethod.GET)
    public Staff profile(HttpServletRequest request) {
        String email = request.getParameter("userId");
        return staffService.showProfile(email);
    }

    @RequestMapping(value = "/staff/modifyAccount", method = RequestMethod.GET)
    public int modifyAccount(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return staffService.modifyAccount(email, name, password, title, collegeId);
    }

    @RequestMapping(value = "/staff/approveCollege", method = RequestMethod.GET)
    public int approveCollege(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return staffService.approveCollege(collegeId, true);
    }

    @RequestMapping(value = "/staff/settleAccount", method = RequestMethod.GET)
    public int settleAccount(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return staffService.settleAccount(collegeId);
    }

    @RequestMapping(value = "/staff/showScores", method = RequestMethod.GET)
    public List<Classes> showScores(HttpServletRequest request) {
        String email = request.getParameter("email");
        return staffService.showScores(email);
    }

    @RequestMapping(value = "/staff/releaseScores", method = RequestMethod.POST)
    @ResponseBody
    public int releaseScores(@RequestBody List<Classes> transcript) {
        return staffService.releaseScores(transcript);
    }

    @RequestMapping(value = "/staff/showAllStaffs", method = RequestMethod.GET)
    public List<Staff> showAllStaffs(HttpServletRequest request) {
        return staffService.showAllStaffs();
    }

    @RequestMapping(value = "/staff/showAllStudents", method = RequestMethod.GET)
    public List<Student> showAllStudents(HttpServletRequest request) {
        return staffService.showAllStudents();
    }

    @RequestMapping(value = "/staff/showAllColleges", method = RequestMethod.GET)
    public List<College> showAllColleges(HttpServletRequest request) {
        return staffService.showAllColleges();
    }

    @RequestMapping(value = "/staff/showAllCourses", method = RequestMethod.GET)
    public List<Course> showAllCourses(HttpServletRequest request) {
        return staffService.showAllCourses();
    }

    @RequestMapping(value = "/staff/showAllOrders", method = RequestMethod.GET)
    public List<Order> showAllOrders(HttpServletRequest request) {
        return staffService.showAllOrders();
    }
}
