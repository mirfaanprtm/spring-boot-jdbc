package com.example.challengespringboot.service;

import com.example.challengespringboot.exception.NotFoundException;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.repository.ITeacherRepository;
import com.example.challengespringboot.utils.TeacherStudentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TeacherService implements ITeacherService{
    @Autowired
    @Qualifier("teachers")
    private ITeacherRepository teacherRepository;

    @Override
    public List<Teacher> list() {
        try {
            List<Teacher> teachers = teacherRepository.getAll();
            if(teachers.isEmpty()){
                throw new NotFoundException("Teacher Not Found");
            }
            return teachers;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher create(Teacher teacher) {
        try {
            Optional<List<Teacher>> teachers = teacherRepository.findBy(TeacherStudentKey.first_name, teacher.getFirst_name());
            if(teacherRepository.getAll().size() <= 8){
                if (teachers.isPresent()){
                    throw new Exception("Data Already Exist");
                }
                return teacherRepository.create(teacher);
            } else {
                throw new NotFoundException("Data is Full");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> get(String id) {
        try {
            List<Teacher> teacher = teacherRepository.findById(id);
            if(teacher.isEmpty()){
                throw new NotFoundException("Teacher ID not found");
            }
            return teacher;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<List<Teacher>> getBy(TeacherStudentKey key, String value) {
        try {
            Optional<List<Teacher>> teachers = teacherRepository.findBy(key, value);
            if(teachers.isEmpty()){
                throw new NotFoundException("Teacher Not Found");
            }
            return teachers;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher update(Teacher teacher, String id) {
        try {
            get(id);
            teacherRepository.update(teacher, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }

    @Override
    public void delete(String id) {
        try {
            get(id);
            teacherRepository.delete(id);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Teacher> addBulk(List<Teacher> bulkTeachers) {
        try {
            return teacherRepository.addBulk(bulkTeachers);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
