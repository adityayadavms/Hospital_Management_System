package com.adityayadavlearning.springboot.hospitalManagement.repository;

import com.adityayadavlearning.springboot.hospitalManagement.entity.RolePermission;
import com.adityayadavlearning.springboot.hospitalManagement.entity.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
    List<RolePermission> findByIdRole(RoleType role);
}
