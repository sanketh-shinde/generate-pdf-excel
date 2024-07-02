package com.sanketh.generatepdfexcel.service;

import com.sanketh.generatepdfexcel.entity.Student;

import java.util.List;

public interface StudentService {

    Student addStudent(Student student);

    List<Student> allStudents();
}
