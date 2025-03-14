package com.hadjshell.SpringJDBCDemo.repo;

import com.hadjshell.SpringJDBCDemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepo {
    private JdbcTemplate jdbc;

    @Autowired
    public StudentRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public StudentRepo() {}

    public void save(Student s) {
        String sql = "insert into student (rollno, name, marks) values (?, ?, ?)";
        int rows  =jdbc.update(sql, s.getRollNo(), s.getName(), s.getMarks());
        System.out.println(rows + " effected.");
    }

    public List<Student> findAll() {
        String sql = "select * from student";
        return jdbc.query(sql, (rs, rowNum) ->
            new Student(rs.getInt("rollno"),
                        rs.getString("name"),
                        rs.getInt("marks"))
        );
    }

    public Student find(int no) {
        String sql = "select * from student where rollno = ?";
        return jdbc.queryForObject(sql, (rs, rowNum) ->
                new Student(rs.getInt("rollno"),
                        rs.getString("name"),
                        rs.getInt("marks")),
                no);
    }
}
