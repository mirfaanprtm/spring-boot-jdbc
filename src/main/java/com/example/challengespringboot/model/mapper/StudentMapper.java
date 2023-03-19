package com.example.challengespringboot.model.mapper;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.model.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getString("student_id"));
        student.setFirst_name(resultSet.getString("first_name"));
        student.setLast_name(resultSet.getString("last_name"));
        student.setEmail(resultSet.getString("email"));
        return student;
    }
}
