package com.example.challengespringboot.controller;

import com.example.challengespringboot.model.Student;
import com.example.challengespringboot.model.Subject;
import com.example.challengespringboot.model.Teacher;
import com.example.challengespringboot.model.request.SubjectRequest;
import com.example.challengespringboot.model.response.SuccessResponse;
import com.example.challengespringboot.service.IStudentService;
import com.example.challengespringboot.service.ISubjectService;
import com.example.challengespringboot.utils.SubjectKey;
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

@RestController
@RequestMapping("/subjects")
@Validated
public class SubjectController {
    @Autowired
    ISubjectService subjectService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllSubject(){
        List<Subject> subjectList = subjectService.list();
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<Subject>>("SUCCESS FINDING", subjectList));
    }
    @PostMapping
    public ResponseEntity createSubject(@Valid @RequestBody SubjectRequest subjectRequest){
        Subject newSubject = modelMapper.map(subjectRequest, Subject.class);

        Subject subject1 = subjectService.create(newSubject);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Subject>("CREATE SUCCESS", subject1));
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") String id){
        List<Subject> subject = subjectService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<List<Subject>>("ID FOUND", subject));
    }
    @GetMapping(params = {"key", "value"})
    public ResponseEntity getBy(@RequestParam String key, @RequestParam String value){
        Optional<List<Subject>> subjects = subjectService.getBy(SubjectKey.valueOf(key), value);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Optional<List<Subject>>>("SUBJECT FOUND", subjects));
    }
    @DeleteMapping
    public ResponseEntity deleteById(@RequestBody String id){
        subjectService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("DELETE SUCCESS", "null"));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@Valid @RequestBody SubjectRequest subjectRequest, @PathVariable String id){
        Subject newSubject = modelMapper.map(subjectRequest, Subject.class);

        subjectService.update(newSubject, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Subject>("UPDATE SUCCESS", newSubject));
    }

    @PostMapping("/addBulk")
    public ResponseEntity addBulk(@RequestBody @Valid List<SubjectRequest> subjectsRequest){
        List<Subject> newSubject = subjectsRequest.stream().map(s -> modelMapper.map(s, Subject.class)).collect(Collectors.toList());
        List<Subject> subjectList = subjectService.addBulk(newSubject);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<List<Subject>>("CREATE BULK SUCCESS", subjectList));
    }
}
