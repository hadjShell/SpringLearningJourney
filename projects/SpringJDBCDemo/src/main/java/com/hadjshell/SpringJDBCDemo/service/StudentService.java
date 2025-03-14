package com.hadjshell.SpringJDBCDemo.service;

import com.hadjshell.SpringJDBCDemo.model.Student;
import com.hadjshell.SpringJDBCDemo.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepo repo;

    public StudentRepo getRepo() {
        return repo;
    }

    @Autowired
    public void setRepo(StudentRepo repo) {
        this.repo = repo;
    }

    public void addStudent(Student s) {
        repo.save(s);
    }

    public void getStudents() {
        List<Student> students = repo.findAll();
        students.forEach(System.out::println);
    }

    public void getStudent(int no) {
        Student student = repo.find(no);
        System.out.println(student);
    }
}
