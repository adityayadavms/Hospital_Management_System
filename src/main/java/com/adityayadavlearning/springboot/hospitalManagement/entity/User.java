package com.adityayadavlearning.springboot.hospitalManagement.entity;

import com.adityayadavlearning.springboot.hospitalManagement.Security.PermissionService;

import com.adityayadavlearning.springboot.hospitalManagement.entity.type.AuthProviderType;
import com.adityayadavlearning.springboot.hospitalManagement.entity.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="app_user", indexes={
        @Index(name="idx_provider_id_provider_type", columnList = "providerId,providerType")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    @ElementCollection(fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles= new HashSet<>();

    @Transient
    private PermissionService permissionService;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (RoleType role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

            if (permissionService != null) {
                authorities.addAll(
                        permissionService.getAuthoritiesForRole(role)
                );
            }
        }

        return authorities;
    }
}
