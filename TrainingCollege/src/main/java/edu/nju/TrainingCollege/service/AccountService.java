package edu.nju.TrainingCollege.service;

import edu.nju.TrainingCollege.dao.StudentMapper;
import edu.nju.TrainingCollege.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    StudentMapper studentMapper;

    public Student getByEmail(String email){
        return studentMapper.getByEmail(email);
    }
}
