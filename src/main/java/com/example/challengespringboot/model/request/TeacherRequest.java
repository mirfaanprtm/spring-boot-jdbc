package com.example.challengespringboot.model.request;

import jakarta.validation.constraints.*;

public class TeacherRequest {

    @NotBlank(message = "{invalid.first_name.required}")
    @Size(message = "first name must be between {min} and {max}", max = 100, min = 3)
    @Pattern(message = "last_name cannot contain {regexp}", regexp = "[^0-9]+")
    private String first_name;

    @NotBlank(message = "{invalid.last_name.required}")
    @Size(message = "last name must be between {min} and {max}", max = 100, min = 3)
    @Pattern(message = "last_name cannot contain {regexp}", regexp = "[^0-9]+")
    private String last_name;
    @NotBlank(message = "{invalid.email.required}")
    @Size(message = "email must be between {min} and {max}", max = 100, min = 3)
    @Email
    private String email;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
