package edu.nju.TrainingCollege.controller;

import edu.nju.TrainingCollege.domain.Classes;
import edu.nju.TrainingCollege.domain.Course;
import edu.nju.TrainingCollege.domain.Order;
import edu.nju.TrainingCollege.domain.Student;
import edu.nju.TrainingCollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @CrossOrigin
    @RequestMapping(value = "/student/login", method = RequestMethod.GET)
    public Student login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession httpSession = request.getSession();
        Student student = studentService.login(email, password);
        if (student != null) {
            httpSession.setAttribute("userId", email);
            httpSession.setAttribute("userType", "student");
        }
        return student;
    }

    @CrossOrigin
    @RequestMapping(value = "/student/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("userId");
        httpSession.removeAttribute("userType");
    }

    @RequestMapping(value = "/student/register", method = RequestMethod.GET)
    public int register(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        return studentService.register(email, name, password);
    }

    @RequestMapping(value = "/student/activate", method = RequestMethod.GET)
    public void activate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String code = request.getParameter("code");
            String email = request.getParameter("email");
            if (studentService.activate(email, code) != 0) {
                response.sendRedirect("/#/home/login?message=Activate successfully!&type=success");
            } else {
                response.sendRedirect("/#/home/login?message=Failed to activate!&type=error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/student/profile", method = RequestMethod.GET)
    public Student profile(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return studentService.showProfile(userId);
    }

    @RequestMapping(value = "/student/modifyAccount", method = RequestMethod.GET)
    public int modifyAccount(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        return studentService.modifyAccount(email, name, password);
    }

    @RequestMapping(value = "/student/modifyMembership", method = RequestMethod.GET)
    public int modifyMembership(HttpServletRequest request) {
        String email = request.getParameter("email");
        int level = Integer.parseInt(request.getParameter("level"));
        return studentService.modifyMembership(email, level);
    }

    @RequestMapping(value = "/student/exchange", method = RequestMethod.GET)
    public int exchange(HttpServletRequest request) {
        String email = request.getParameter("email");
        int exchange = Integer.parseInt(request.getParameter("exchange"));
        return studentService.exchangeCredits(email, exchange);
    }

    @RequestMapping(value = "/student/showScores", method = RequestMethod.GET)
    public List<Classes> showScores(HttpServletRequest request) {
        String email = request.getParameter("email");
        return studentService.showScores(email);
    }

    @RequestMapping(value = "/student/showCourses", method = RequestMethod.GET)
    public List<Course> showCourses(HttpServletRequest request) {
        String email = request.getParameter("email");
        return studentService.showCourses(email);
    }

    @RequestMapping(value = "/student/showOrders", method = RequestMethod.GET)
    public List<Order> showOrders(HttpServletRequest request) {
        String email = request.getParameter("email");
        return studentService.showOrders(email);
    }

    @RequestMapping(value = "/student/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> subscribe(@RequestBody Order order) {
        if (order.getId() != 0)
            if (studentService.subscribe(order.getEmail(), order.getId(), order.getAmount(), order.getStudentList()) != 0)
                return studentService.showOrders(order.getEmail());
        if (order.getCollege() != 0)
            if (!(order.getType() == null || order.getType().isEmpty()))
                if (studentService.subscribe(order.getEmail(), order.getCollege(), order.getType(), order.getAmount(), order.getStudentList()) != 0)
                    return studentService.showOrders(order.getEmail());
        return null;
    }

    @RequestMapping(value = "/student/unsubscribe", method = RequestMethod.GET)
    public int unsubscribe(HttpServletRequest request) {
        String email = request.getParameter("email");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        return studentService.unsubscribe(email, orderId);
    }

    @RequestMapping(value = "/student/pay", method = RequestMethod.GET)
    public int pay(HttpServletRequest request) {
        String email = request.getParameter("email");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        return studentService.pay(email, orderId);
    }
}
