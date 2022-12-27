package com.jwttoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwttoken.entity.Student;
@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{

	Student findStudentByUsername(String username);

}
