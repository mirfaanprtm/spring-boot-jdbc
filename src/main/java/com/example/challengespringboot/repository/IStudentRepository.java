package com.example.challengespringboot.repository;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.utils.TeacherStudentKey;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    List<Student> getAll() throws Exception;
    Student create(Student student) throws Exception;
    List<Student> findById(String id) throws Exception;
    Optional<List<Student>> findBy(TeacherStudentKey key, String value) throws Exception;
    void update(Student student, String id) throws Exception;
    void delete(String id) throws Exception;
    List<Student> addBulk(List<Student> bulkStudents) throws Exception;
}
