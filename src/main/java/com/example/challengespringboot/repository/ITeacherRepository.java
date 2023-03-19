package com.example.challengespringboot.repository;

import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.utils.TeacherStudentKey;

import java.util.List;
import java.util.Optional;

public interface ITeacherRepository {
    List<Teacher> getAll() throws Exception;
    Teacher create(Teacher teacher) throws Exception;
    List<Teacher> findById(String id) throws Exception;
    Optional<List<Teacher>> findBy(TeacherStudentKey key, String value) throws Exception;
    void update(Teacher teacher, String id) throws Exception;
    void delete(String id) throws Exception;
    List<Teacher> addBulk(List<Teacher> bulkTeachers) throws Exception;
}
