package com.example.challengespringboot.controller;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.model.request.StudentRequest;
import com.example.challengespringboot.model.response.SuccessResponse;
import com.example.challengespringboot.repository.StudentRepository;
import com.example.challengespringboot.service.IStudentService;
import com.example.challengespringboot.utils.TeacherStudentKey;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {
    @Autowired
    IStudentService studentService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllCourseStudent(){
        List<Student> studentList = studentService.list();
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<Student>>("SUCCESS FINDING", studentList));
    }
    @PostMapping
    public ResponseEntity createStudent(@Valid @RequestBody StudentRequest studentRequest){
        Student newStudent = modelMapper.map(studentRequest, Student.class);

        Student student1 = studentService.create(newStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Student>("CREATE SUCCESS", student1));
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") String id){
        List<Student> student = studentService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<Student>>("ID FOUND", student));
    }
    @GetMapping(params = {"key", "value"})
    public ResponseEntity getBy(@RequestParam String key, @RequestParam String value){
        Optional<List<Student>> students = studentService.getBy(TeacherStudentKey.valueOf(key), value);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Optional<List<Student>>>("STUDENT FOUND", students));
    }

    @DeleteMapping
    public ResponseEntity deleteById(@RequestBody String id){
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("DELETE SUCCESS", "null"));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@Valid @RequestBody StudentRequest studentRequest, @PathVariable String id){
        Student newStudent = modelMapper.map(studentRequest, Student.class);
        studentService.update(newStudent, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Student>("UPDATE SUCCESS", newStudent));
    }

    @PostMapping("/addBulk")
    public ResponseEntity addBulk(@RequestBody @Valid List<StudentRequest> studentsRequest){
        List<Student> newStudent = studentsRequest.stream().map(t -> modelMapper.map(t, Student.class)).collect(Collectors.toList());

        List<Student> studentList = studentService.addBulk(newStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<List<Student>>("CREATE BULK SUCCESS", studentList));
    }
}
