package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloWorldController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String Hello(HttpServletRequest request) {
        String email = request.getParameter("email");
        return accountService.getByEmail(email).getName();
    }
}
