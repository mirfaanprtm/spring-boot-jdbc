package com.example.challengespringboot.service;

import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.utils.TeacherStudentKey;

import java.util.List;
import java.util.Optional;

public interface ITeacherService {
    List<Teacher> list();

    Teacher create(Teacher teacher);
    List<Teacher> get(String id);
    Optional<List<Teacher>> getBy(TeacherStudentKey key, String value);
    Teacher update(Teacher teacher, String id);
    void delete(String id);
    List<Teacher> addBulk(List<Teacher> bulkTeachers);
}
