package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.Staff;
import edu.nju.TrainingCollege.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class StaffController {
    @Autowired
    StaffService staffService;

    @RequestMapping(value = "/staff/login", method = RequestMethod.GET)
    public Staff Login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession httpSession = request.getSession();
        Staff staff = staffService.login(email, password);
        if (staff != null) {
            httpSession.setAttribute("userid", email);
            httpSession.setAttribute("usertype", "staff");
        }
        return staff;
    }

    @RequestMapping(value = "/staff/logout", method = RequestMethod.GET)
    public void Logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("userid");
        httpSession.removeAttribute("usertype");
    }

    @RequestMapping(value = "/staff/register", method = RequestMethod.GET)
    public int Register(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        return staffService.register(email, name, password, title, collegeId);
    }
}
