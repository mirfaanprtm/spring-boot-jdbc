package com.example.challengespringboot.repository;

import com.example.challengespringboot.exception.NotFoundException;
import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.utils.IRandomStringGenerator;
import com.example.challengespringboot.utils.TeacherStudentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentArrayRepository implements IStudentRepository{
    private TeacherStudentKey teacherStudentKey;
    @Autowired
    IRandomStringGenerator randomStringGenerator;
    private List<Student> students = new ArrayList<>();
    @Override
    public List<Student> getAll() throws Exception {
        return students;
    }

    @Override
    public Student create(Student student) throws Exception {
        student.setStudentId(randomStringGenerator.random());
        students.add(student);
        return student;
    }

    @Override
    public List<Student> findById(String id) throws Exception {
        for(Student student : students){
            if(student.getStudentId().equals(id)){
                return List.of(student);
            }
        }
        return List.of();
    }

    @Override
    public Optional<List<Student>> findBy(TeacherStudentKey key, String value) throws Exception {
        List<Student> studentList = new ArrayList<>();
            switch (key){
                case first_name -> {
                    for(Student student : students) {
                        if (student.getFirst_name().toLowerCase().equals(value.toLowerCase())) {
                            studentList.add(student);
                        }
                    }
                }
                case last_name -> {
                    for(Student student : students) {
                        if (student.getLast_name().toLowerCase().equals(value.toLowerCase())) {
                            studentList.add(student);
                        }
                    }
                }
                case email -> {
                    for(Student student : students) {
                        if (student.getEmail().toLowerCase().equals(value.toLowerCase())) {
                            studentList.add(student);
                        }
                    }
                }
            }
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    @Override
    public void update(Student student, String id) throws Exception {
        for(Student existingStudent : students){
            if(existingStudent.getStudentId().equals(id)){
                existingStudent.setFirst_name(student.getFirst_name());
                existingStudent.setLast_name(student.getLast_name());
                existingStudent.setEmail(student.getEmail());
                break;
            }
        }
    }

    @Override
    public void delete(String id) throws Exception {
        for(Student student : students){
            if(student.getStudentId().equals(id)){
                students.remove(student);
                break;
            }
        }
    }
    @Override
    public List<Student> addBulk(List<Student> bulkStudents) throws Exception {
        for(Student student : bulkStudents){
            Optional<List<Student>> exitstingStudent = findBy(TeacherStudentKey.first_name, student.getFirst_name());
            if(getAll().size() <= 24){
                if (exitstingStudent.isPresent()){
                    throw new Exception("Data Already Exist");
                }
                student.setStudentId(randomStringGenerator.random());
                students.add(student);
            } else {
                throw new NotFoundException("Data is Full");
            }
        }
        return bulkStudents;
    }

}
