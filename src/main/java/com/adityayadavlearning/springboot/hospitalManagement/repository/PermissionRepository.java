package com.adityayadavlearning.springboot.hospitalManagement.repository;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Optional<Permission> findByName(String name);
}
