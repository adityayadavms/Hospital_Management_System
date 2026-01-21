package com.adityayadavlearning.springboot.hospitalManagement.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="role_permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("permissionId")
    @JoinColumn(name="permission_id", nullable=false)
    private Permission permission;

}
