package edu.nju.TrainingCollege.service.impl;

import edu.nju.TrainingCollege.dao.StudentMapper;
import edu.nju.TrainingCollege.domain.Student;
import edu.nju.TrainingCollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("StudentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Student login(String email, String password) {
        Student student = studentMapper.getByEmail(email);
        if (student != null) {
            if (student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    public int register(String email, String name, String password) {
        return studentMapper.insertStudent(email, name, password);
    }

    public int modifyMembership(String email, int level) {
        return studentMapper.updateLevelByEmail(level, email);
    }

    @Transactional
    public int modifyAccount(String email, String name, String password) {
        Student student = studentMapper.getByEmail(email);
        if (student != null) {
            studentMapper.updateNameByEmail(name, email);
            studentMapper.updatePasswordByEmail(password, email);
            return 1;
        }
        return 0;
    }

    @Transactional
    public int exchangeCredits(String email, int credit) {
        Student student = studentMapper.getByEmail(email);
        if (student != null) {
            if (student.getPoint() > credit) {
                studentMapper.updateDepositByEmail(student.getDeposit() + credit, email);
                studentMapper.updatePointByEmail(student.getPoint() - credit, email);
                return 1;
            }
        }
        return 0;
    }

    public int getDiscount(String email) {
        Student student = studentMapper.getByEmail(email);
        if (student != null) {
            return student.getLevel();
        }
        return 0;
    }

    @Transactional
    public int save(String email, int amount) {
        Student student = studentMapper.getByEmail(email);
        if (student != null) {
            studentMapper.updateDepositByEmail(student.getDeposit() + amount, email);
            return 1;
        }
        return 0;
    }

    @Transactional
    public int spend(String email, int amount) {
        Student student = studentMapper.getByEmail(email);
        if (student != null) {
            if (student.getDeposit() > amount) {
                studentMapper.updateDepositByEmail(student.getDeposit() - amount, email);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int subscribe(String email, String courseid) {
        return 0;
    }

    @Override
    public int unsubscribe(String email, String courseid) {
        return 0;
    }

    @Override
    public int showOrders(String email) {
        return 0;
    }

    @Override
    public int showCourses(String email) {
        return 0;
    }


}
