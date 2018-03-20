package edu.nju.TrainingCollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class TrainingCollegeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingCollegeApplication.class, args);
	}
}
