package com.sanketh.generatepdfexcel.repository;

import com.sanketh.generatepdfexcel.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
