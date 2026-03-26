package com.example.springboot.hospitalManagement.Repository;

import com.example.springboot.hospitalManagement.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}