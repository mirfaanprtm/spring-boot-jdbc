package com.example.challengespringboot.model.mapper;

import com.example.challengespringboot.model.Subject;
import com.example.challengespringboot.model.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(resultSet.getString("subject_id"));
        subject.setSubject_name(resultSet.getString("subject_name"));
        return subject;
    }
}
