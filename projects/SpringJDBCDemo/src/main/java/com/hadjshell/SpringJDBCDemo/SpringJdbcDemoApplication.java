package com.hadjshell.SpringJDBCDemo;

import com.hadjshell.SpringJDBCDemo.model.Student;
import com.hadjshell.SpringJDBCDemo.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringJdbcDemoApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SpringJdbcDemoApplication.class, args);

		Student s = context.getBean(Student.class);
		s.setRollNo(104);
		s.setMarks(100);
		s.setName("David");

		StudentService ss = context.getBean(StudentService.class);
		ss.addStudent(s);
		ss.getStudents();
		ss.getStudent(104);
	}

}
