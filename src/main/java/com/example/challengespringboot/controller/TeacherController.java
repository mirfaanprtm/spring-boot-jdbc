package com.example.challengespringboot.controller;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.model.request.TeacherRequest;
import com.example.challengespringboot.model.response.SuccessResponse;
import com.example.challengespringboot.service.IStudentService;
import com.example.challengespringboot.service.ITeacherService;
import com.example.challengespringboot.utils.TeacherStudentKey;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/teachers")
@Validated
public class TeacherController {
    @Autowired
    ITeacherService teacherService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllTeacher(){
        List<Teacher> teacherList = teacherService.list();
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<Teacher>>("SUCCESS FINDING", teacherList));
    }
    @PostMapping
    public ResponseEntity createTeacher(@Valid @RequestBody TeacherRequest teacherRequest){
        Teacher newTeacher = modelMapper.map(teacherRequest, Teacher.class);

        Teacher teacher1 = teacherService.create(newTeacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Teacher>("CREATE SUCCESS", teacher1));
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") String id){
        List<Teacher> teacher = teacherService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<Teacher>>("ID FOUND", teacher));
    }
    @GetMapping(params = {"key", "value"})
    public ResponseEntity getBy(@RequestParam String key, @RequestParam String value){
        Optional<List<Teacher>> teachers = teacherService.getBy(TeacherStudentKey.valueOf(key), value);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Optional<List<Teacher>>>("TEACHER FOUND", teachers));
    }
    @DeleteMapping
    public ResponseEntity deleteById(@RequestBody String id){
        teacherService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("DELETE SUCCESS", "null"));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@Valid @RequestBody TeacherRequest teacherRequest, @PathVariable String id){
        Teacher newTeacher = modelMapper.map(teacherRequest, Teacher.class);

        teacherService.update(newTeacher, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Teacher>("UPDATE SUCCESS", newTeacher));
    }

    @PostMapping("/addBulk")
    public ResponseEntity addBulk(@RequestBody List<@Valid TeacherRequest> teachersRequest){
        List<Teacher> newTeacher = teachersRequest.stream().map(t -> modelMapper.map(t, Teacher.class)).collect(Collectors.toList());
        List<Teacher> teacherList = teacherService.addBulk(newTeacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<List<Teacher>>("CREATE BULK SUCCESS", teacherList));
    }
}
