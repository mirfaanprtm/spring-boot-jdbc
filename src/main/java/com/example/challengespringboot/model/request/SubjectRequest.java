package com.example.challengespringboot.model.request;

import jakarta.validation.constraints.*;

public class SubjectRequest {
    @NotBlank(message = "{invalid.subject_name.required}")
    @Size(message = "subject name must be between {min} and {max}", max = 100, min = 3)
    private String subject_name;
    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
