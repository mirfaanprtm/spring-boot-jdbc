package com.example.challengespringboot.repository;

import com.example.challengespringboot.exception.NotFoundException;
import com.example.challengespringboot.model.Subject;
import com.example.challengespringboot.utils.IRandomStringGenerator;
import com.example.challengespringboot.utils.SubjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SubjectArrayRepository implements ISubjectRepository{
    private SubjectKey subjectKey;
    @Autowired
    IRandomStringGenerator randomStringGenerator;
    private List<Subject> subjects = new ArrayList<>();
    @Override
    public List<Subject> getAll() throws Exception {
        return subjects;
    }

    @Override
    public Subject create(Subject subject) throws Exception {
        subject.setSubjectId(randomStringGenerator.random());
        subjects.add(subject);
        return subject;
    }

    @Override
    public List<Subject> findById(String id) throws Exception {
        for(Subject subject : subjects){
            if(subject.getSubjectId().equals(id)){
                return List.of(subject);
            }
        }
        return List.of();
    }

    @Override
    public Optional<List<Subject>> findBy(SubjectKey key, String value) throws Exception {
        List<Subject> subjectList = new ArrayList<>();
            switch (key){
                case subject_name -> {
                    for(Subject subject : subjects) {
                        if (subject.getSubject_name().toLowerCase().equals(value.toLowerCase())) {
                            subjectList.add(subject);
                        }
                    }
                }
            }
        return subjectList.isEmpty() ? Optional.empty() : Optional.of(subjectList);
    }

    @Override
    public void update(Subject subject, String id) throws Exception {
        for(Subject existingSubject : subjects){
            if(existingSubject.getSubjectId().equals(id)){
                existingSubject.setSubject_name(subject.getSubject_name());
                break;
            }
        }
    }

    @Override
    public void delete(String id) throws Exception {
        for(Subject subject : subjects){
            if(subject.getSubjectId().equals(id)){
                subjects.remove(subject);
                break;
            }
        }
    }

    @Override
    public List<Subject> addBulk(List<Subject> bulkSubjects) throws Exception {
        for(Subject subject : bulkSubjects){
            Optional<List<Subject>> exitstingSubject = findBy(SubjectKey.subject_name, subject.getSubject_name());
            if(getAll().size() <= 8){
                if (exitstingSubject.isPresent()){
                    throw new Exception("Data Already Exist");
                }
                subject.setSubjectId(randomStringGenerator.random());
                subjects.add(subject);
            } else {
                throw new NotFoundException("Data is Full");
            }
        }
        return bulkSubjects;
    }
}
