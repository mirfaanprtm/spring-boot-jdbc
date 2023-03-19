package com.example.challengespringboot.repository;

import com.example.challengespringboot.exception.NotFoundException;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.utils.IRandomStringGenerator;
import com.example.challengespringboot.utils.TeacherStudentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherArrayRepository implements ITeacherRepository{
    private TeacherStudentKey teacherStudentKey;
    @Autowired
    IRandomStringGenerator randomStringGenerator;
    private List<Teacher> teachers = new ArrayList<>();
    @Override
    public List<Teacher> getAll() throws Exception {
        return teachers;
    }

    @Override
    public Teacher create(Teacher teacher) throws Exception {
        teacher.setTeacherId(randomStringGenerator.random());
        teachers.add(teacher);
        return teacher;
    }

    @Override
    public List<Teacher> findById(String id) throws Exception {
        for(Teacher teacher : teachers){
            if(teacher.getTeacherId().equals(id)){
                return List.of(teacher);
            }
        }
        return List.of();
    }

    @Override
    public Optional<List<Teacher>> findBy(TeacherStudentKey key, String value) throws Exception {
        List<Teacher> teacherList = new ArrayList<>();
            switch (key){
                case first_name -> {
                    for(Teacher teacher : teachers) {
                        if (teacher.getFirst_name().toLowerCase().equals(value.toLowerCase())) {
                            teacherList.add(teacher);
                        }
                    }
                }
                case last_name -> {
                    for(Teacher teacher : teachers) {
                        if (teacher.getLast_name().toLowerCase().equals(value.toLowerCase())) {
                            teacherList.add(teacher);
                        }
                    }
                }
                case email -> {
                    for(Teacher teacher : teachers) {
                        if (teacher.getEmail().toLowerCase().equals(value.toLowerCase())) {
                            teacherList.add(teacher);
                        }
                    }
                }
            }
        return teacherList.isEmpty() ? Optional.empty() : Optional.of(teacherList);
    }

    @Override
    public void update(Teacher teacher, String id) throws Exception {
        for(Teacher existingTeacher : teachers){
            if(existingTeacher.getTeacherId().equals(id)){
                existingTeacher.setFirst_name(teacher.getFirst_name());
                existingTeacher.setLast_name(teacher.getLast_name());
                existingTeacher.setEmail(teacher.getEmail());
                break;
            }
        }
    }

    @Override
    public void delete(String id) throws Exception {
        for(Teacher teacher : teachers){
            if(teacher.getTeacherId().equals(id)){
                teachers.remove(teacher);
                break;
            }
        }
    }

    @Override
    public List<Teacher> addBulk(List<Teacher> bulkTeachers) throws Exception {
        for(Teacher teacher : bulkTeachers){
            Optional<List<Teacher>> exitstingTeacher = findBy(TeacherStudentKey.first_name, teacher.getFirst_name());
            if(getAll().size() <= 5){
                if (exitstingTeacher.isPresent()){
                    throw new Exception("Data Already Exist");
                }
                teacher.setTeacherId(randomStringGenerator.random());
                teachers.add(teacher);
            } else {
                throw new NotFoundException("Data is Full");
            }
        }
        return bulkTeachers;
    }
}
