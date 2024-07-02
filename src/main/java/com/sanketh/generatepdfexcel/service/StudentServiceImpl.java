package com.sanketh.generatepdfexcel.service;

import com.sanketh.generatepdfexcel.entity.Student;
import com.sanketh.generatepdfexcel.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }
}
