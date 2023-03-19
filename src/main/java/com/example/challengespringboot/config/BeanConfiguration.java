package com.example.challengespringboot.config;

import com.example.challengespringboot.repository.IStudentRepository;
import com.example.challengespringboot.repository.StudentRepository;
import com.example.challengespringboot.service.IStudentService;
import com.example.challengespringboot.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class BeanConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
