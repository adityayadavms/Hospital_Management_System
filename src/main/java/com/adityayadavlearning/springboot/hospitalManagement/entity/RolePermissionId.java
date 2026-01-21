package com.adityayadavlearning.springboot.hospitalManagement.entity;


import com.adityayadavlearning.springboot.hospitalManagement.entity.type.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RolePermissionId implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name= "role")
    private RoleType role;

    @Column(name = "permission_id")
    private Long permissionId;

}
