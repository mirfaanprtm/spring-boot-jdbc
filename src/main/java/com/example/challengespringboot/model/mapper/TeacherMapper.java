package com.example.challengespringboot.model.mapper;

import com.example.challengespringboot.model.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(resultSet.getString("teachers_id"));
        teacher.setFirst_name(resultSet.getString("first_name"));
        teacher.setLast_name(resultSet.getString("last_name"));
        teacher.setEmail(resultSet.getString("email"));
        return teacher;
    }
}
