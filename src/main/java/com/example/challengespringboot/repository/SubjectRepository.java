package com.example.challengespringboot.repository;

import com.example.challengespringboot.exception.NotFoundException;
import com.example.challengespringboot.model.Subject;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.model.mapper.SubjectMapper;
import com.example.challengespringboot.model.mapper.TeacherMapper;
import com.example.challengespringboot.utils.IRandomStringGenerator;
import com.example.challengespringboot.utils.SubjectKey;
import com.example.challengespringboot.utils.TeacherStudentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("subjects")
public class SubjectRepository implements ISubjectRepository{
    @Autowired
    IRandomStringGenerator randomStringGenerator;

    private JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ALL = "select * from subjects";
    private final String SQL_GET_BY_ID= "select * from subjects where subject_id=?";
    private final String SQL_CREATE = "insert into subjects values(?,?)";
    private final String SQL_UPDATE = "update subjects set subject_name = ? where subject_id = ?";
    private final String SQL_DELETE = "delete from subjects where subject_id = ?";
    private final String SQL_ADD_BULK = "insert into subjects values(?,?)";
    private String SQL_GET_BY = "select * from subjects where name= ?";
    public SubjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Subject> getAll() throws Exception {
        return jdbcTemplate.query(SQL_GET_ALL, new SubjectMapper());
    }

    @Override
    public Subject create(Subject subject) throws Exception {
        subject.setSubjectId(randomStringGenerator.random());
        jdbcTemplate.update(SQL_CREATE, subject.getSubjectId(), subject.getSubject_name());
        return subject;
    }

    @Override
    public List<Subject> findById(String id) throws Exception {
        return jdbcTemplate.query(SQL_GET_BY_ID, new SubjectMapper(), id);
    }

    @Override
    public Optional<List<Subject>> findBy(SubjectKey key, String value) throws Exception {
        String sql  = SQL_GET_BY.replace("name", key.toString());
        List<Subject> subjectList = jdbcTemplate.query(sql, new SubjectMapper(), value);

        return subjectList.isEmpty() ? Optional.empty() : Optional.of(subjectList);
    }

    @Override
    public void update(Subject subject, String id) throws Exception {
        jdbcTemplate.update(SQL_UPDATE, subject.getSubject_name(), id);
    }

    @Override
    public void delete(String id) throws Exception {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Subject> addBulk(List<Subject> bulkSubjects) throws Exception {
        for(Subject subject : bulkSubjects){
            subject.setSubjectId(randomStringGenerator.random());
            int result = jdbcTemplate.update(SQL_ADD_BULK, subject.getSubjectId(), subject.getSubject_name());
            System.out.println("Create Data Success");
            if(result <= 0){
                throw new Exception("Failed to create book");
            }
        }
        return bulkSubjects;
    }
}
