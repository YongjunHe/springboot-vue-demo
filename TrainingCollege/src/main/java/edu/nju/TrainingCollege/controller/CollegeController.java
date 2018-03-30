package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.College;
import edu.nju.TrainingCollege.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @RequestMapping(value = "/college/login", method = RequestMethod.GET)
    public College Login(HttpServletRequest request) {
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        String password = request.getParameter("password");
        HttpSession httpSession = request.getSession();
        College college = collegeService.login(collegeId, password);
        if (college != null) {
            httpSession.setAttribute("userid", collegeId);
            httpSession.setAttribute("usertype", "college");
        }
        return college;
    }

    @RequestMapping(value = "/college/logout", method = RequestMethod.GET)
    public void Logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("userid");
        httpSession.removeAttribute("usertype");
    }

    @RequestMapping(value = "/college/register", method = RequestMethod.GET)
    public int Register(HttpServletRequest request) {
        String password = request.getParameter("password");
        String location = request.getParameter("location");
        return collegeService.register(password, location);
    }
}
