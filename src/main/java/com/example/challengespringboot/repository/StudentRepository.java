package com.example.challengespringboot.repository;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.model.mapper.StudentMapper;
import com.example.challengespringboot.utils.IRandomStringGenerator;
import com.example.challengespringboot.utils.TeacherStudentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
@Qualifier("students")
public class StudentRepository implements IStudentRepository{
    private TeacherStudentKey teacherStudentKey;
    @Autowired
    IRandomStringGenerator randomStringGenerator;
    private JdbcTemplate jdbcTemplate;
    private final String SQL_GET_ALL = "select * from students";
    private final String SQL_GET_BY_ID= "select * from students where student_id=?";
    private final String SQL_CREATE = "insert into students values(?,?,?,?)";
    private final String SQL_UPDATE = "update students set first_name = ?, last_name = ?, email = ? where student_id = ?";
    private final String SQL_DELETE = "delete from students where student_id = ?";
    private final String SQL_ADD_BULK = "insert into students values(?, ?,?,?)";
    private String SQL_GET_BY = "select * from students where name= ?";
    private ArrayList<Student> students = new ArrayList<>();

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> getAll() throws Exception {
        return jdbcTemplate.query(SQL_GET_ALL, new StudentMapper());
    }

    @Override
    public Student create(Student student) throws Exception {
        student.setStudentId(randomStringGenerator.random());
        jdbcTemplate.update(SQL_CREATE, student.getStudentId(), student.getFirst_name(), student.getLast_name(), student.getEmail());
        return student;
    }

    @Override
    public List<Student> findById(String id) throws Exception {
        return jdbcTemplate.query(SQL_GET_BY_ID, new StudentMapper(), id);
    }

    @Override
    public Optional<List<Student>> findBy(TeacherStudentKey key, String value) throws Exception {
        String sql = SQL_GET_BY.replace("name", key.toString());

        List<Student> studentList = jdbcTemplate.query(sql, new StudentMapper(), value);
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    @Override
    public void update(Student student, String id) throws Exception {
        jdbcTemplate.update(SQL_UPDATE, student.getFirst_name(), student.getLast_name(), student.getEmail(), id);
    }

    @Override
    public void delete(String id) throws Exception {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Student> addBulk(List<Student> bulkStudents) throws Exception {
            for(Student student : students){
                student.setStudentId(randomStringGenerator.random());
                int result = jdbcTemplate.update(SQL_ADD_BULK, student.getStudentId(), student.getFirst_name(), student.getLast_name(), student.getEmail());
                System.out.println("Create Data Success");
                if(result <= 0){
                    throw new Exception("Failed to create book");
                }
            }
            return bulkStudents;
    }
}
