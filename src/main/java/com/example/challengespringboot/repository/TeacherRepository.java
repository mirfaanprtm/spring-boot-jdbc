package com.example.challengespringboot.repository;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.model.mapper.TeacherMapper;
import com.example.challengespringboot.utils.IRandomStringGenerator;
import com.example.challengespringboot.utils.TeacherStudentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("teachers")
public class TeacherRepository implements ITeacherRepository {
    @Autowired
    IRandomStringGenerator randomStringGenerator;
    private JdbcTemplate jdbcTemplate;
    private final String SQL_GET_ALL = "select * from teachers";
    private final String SQL_GET_BY_ID= "select * from teachers where teachers_id=?";
    private final String SQL_CREATE = "insert into teachers values(?,?,?,?)";
    private final String SQL_UPDATE = "update teachers set first_name = ?, last_name = ?, email = ? where teachers_id = ?";
    private final String SQL_DELETE = "delete from teachers where teachers_id = ?";
    private final String SQL_ADD_BULK = "insert into teachers values(?, ?,?,?)";
    private ArrayList<Teacher> teachers = new ArrayList<>();
    public TeacherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Teacher> getAll() throws Exception {
            return jdbcTemplate.query(SQL_GET_ALL, new TeacherMapper());
    }

    @Override
    public Teacher create(Teacher teacher) throws Exception {
            teacher.setTeacherId(randomStringGenerator.random());
            jdbcTemplate.update(SQL_CREATE, teacher.getTeacherId(), teacher.getFirst_name(), teacher.getLast_name(), teacher.getEmail());
            return teacher;
    }

    @Override
    public List<Teacher> findById(String id) throws Exception {
            return jdbcTemplate.query(SQL_GET_BY_ID, new TeacherMapper(), id);
    }

    @Override
    public Optional<List<Teacher>> findBy(TeacherStudentKey key, String value) throws Exception {
        return Optional.empty();
    }

    @Override
    public void update(Teacher teacher, String id) throws Exception {
            jdbcTemplate.update(SQL_UPDATE, teacher.getFirst_name(), teacher.getLast_name(), teacher.getEmail(), id);
    }

    @Override
    public void delete(String id) throws Exception {
            jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Teacher> addBulk(List<Teacher> bulkTeachers) throws Exception {
            for(Teacher teacher : teachers){
                teacher.setTeacherId(randomStringGenerator.random());
                int result = jdbcTemplate.update(SQL_ADD_BULK, teacher.getTeacherId(), teacher.getFirst_name(), teacher.getLast_name(), teacher.getEmail());
                System.out.println("Create Data Success");
                if(result <= 0){
                    throw new Exception("Failed to create book");
                }
            }
            return bulkTeachers;
    }
}
