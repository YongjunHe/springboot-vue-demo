package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.Staff;
import edu.nju.TrainingCollege.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
}
