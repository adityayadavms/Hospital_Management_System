package com.adityayadavlearning.springboot.hospitalManagement.Security;

import com.adityayadavlearning.springboot.hospitalManagement.entity.RolePermission;

import com.adityayadavlearning.springboot.hospitalManagement.entity.type.RoleType;
import com.adityayadavlearning.springboot.hospitalManagement.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionService {

       private final RolePermissionRepository rolePermissionRepository;

       public Set<SimpleGrantedAuthority>  getAuthoritiesForRole(RoleType role){
           List<RolePermission> mappings = rolePermissionRepository.findByIdRole(role);

           Set<SimpleGrantedAuthority> authorities = new HashSet<>();

           for(RolePermission rp:mappings){
               authorities.add(new SimpleGrantedAuthority(rp.getPermission().getName()));
           }

           return authorities;
       }


}
