package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.College;
import edu.nju.TrainingCollege.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
}
