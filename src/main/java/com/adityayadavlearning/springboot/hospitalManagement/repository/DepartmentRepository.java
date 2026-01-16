package com.adityayadavlearning.springboot.hospitalManagement.repository;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}